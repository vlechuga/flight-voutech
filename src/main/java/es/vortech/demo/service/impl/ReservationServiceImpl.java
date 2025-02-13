package es.vortech.demo.service.impl;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.dto.ReservationDto;
import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.entity.Reservation;
import es.vortech.demo.entity.Seat;
import es.vortech.demo.exception.ConstraintException;
import es.vortech.demo.exception.NotFoundException;
import es.vortech.demo.mapper.ReservationMapper;
import es.vortech.demo.notification.PublishService;
import es.vortech.demo.repository.ReservationRepository;
import es.vortech.demo.service.FlightService;
import es.vortech.demo.service.ReservationService;
import es.vortech.demo.service.SeatService;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final FlightService flightService;
    private final SeatService seatService;
    private final ReservationRepository reservationRepository;
    private final PublishService publishService;

    @Override
    public FlightDto getConfirmedReservations(String flightCode) throws NotFoundException {
        final FlightDto flightDto = flightService.findFlightByFlightCode(flightCode);
        List<Reservation> reservations = reservationRepository.findReservationsByFlight_FlightIdAndStatusEquals(flightDto.getFlightId(), Reservation.Status.CONFIRMED);
        flightDto.setSeats(null);
        flightDto.setReservations(reservations.stream().map(ReservationMapper::toDto).toList());
        return flightDto;
    }

    @Override
    public void createReservation(String flightCode, String seatCode) throws ConstraintException, NotFoundException {
        final FlightDto flightDto = flightService.findFlightByFlightCode(flightCode);
        final SeatDto seatDto = seatService.findAvailableSeatByFlightCodeAndSeatCode(flightDto.getFlightId(), seatCode);
        final RequestDto requestDto = RequestDto.builder().flightId(seatDto.getFlight().getFlightId()).seatId(seatDto.getSeatId()).build();
        publishService.doSendReservationRequest(requestDto);
    }

    @Override
    @Transactional
    public void cancelAllReservationsByFlightCode(String flightCode) throws NotFoundException {
        FlightDto flightDto = getConfirmedReservations(flightCode);
        for(ReservationDto reservationDto:  flightDto.getReservations()) {
            cancelReservation(reservationDto.getReservationId());
        }
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId) throws NotFoundException {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        if (Reservation.Status.CANCELED.equals(reservation.getStatus())) {
            return;
        }
        reservationRepository.updateStatus(reservationId, Reservation.Status.CANCELED);
        seatService.updateAvailable(reservation.getSeat(), true);
    }

    @Override
    @Async
    @Retryable(
            retryFor = {OptimisticLockException.class},
            backoff = @Backoff(delay = 1000)
    )
    @Transactional
    public void processReservation(RequestDto requestDto) {
        Optional<Seat> seatJpa = seatService.findById(requestDto.getSeatId());
        if (seatJpa.isEmpty())
            return;

        Seat seat = seatJpa.get();
        if (!seat.isAvailable()) {
            requestDto.setMessage("Seat already reserved");
            doSendRejectedReservationMessage(requestDto);
            return;
        }
        seat.setAvailable(false);
        seatService.save(seat);

        Reservation reservation = Reservation.builder()
                .flight(seat.getFlight())
                .seat(seat)
                .status(Reservation.Status.CONFIRMED)
                .build();
        reservationRepository.save(reservation);

        doSendConfirmedReservationMessage(ReservationMapper.toDto(reservation));
    }

    @Override
    public void doSendRejectedReservationMessage(RequestDto requestDto) {
        publishService.doSendRejectedReservation(requestDto);
    }

    private void doSendConfirmedReservationMessage(ReservationDto reservationDto) {
        publishService.doSendConfirmedReservation(reservationDto);
    }
}
