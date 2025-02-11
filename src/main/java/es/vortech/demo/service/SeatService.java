package es.vortech.demo.service;

import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.entity.Seat;
import es.vortech.demo.exception.ConstraintException;

import java.util.List;
import java.util.Optional;

public interface SeatService {
    Optional<Seat> findById(long seatId);
    SeatDto findAvailableSeatByFlightCodeAndSeatCode(long flightId, String seatCode) throws ConstraintException;
    void updateAvailable(Seat seat, boolean available);
    void save(Seat seat);
    List<SeatDto> getSeatsAvailable(long flightId);
}
