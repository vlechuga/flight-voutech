package es.vortech.demo.service.impl;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.entity.Flight;
import es.vortech.demo.entity.Seat;
import es.vortech.demo.exception.NotFoundException;
import es.vortech.demo.mapper.FlightMapper;
import es.vortech.demo.repository.FlightRepository;
import es.vortech.demo.service.FlightService;
import es.vortech.demo.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final SeatService seatService;

    @Override
    @Cacheable(value = "flight_cache", key = "#flightCode")
    public FlightDto findFlightByFlightCode(String flightCode) throws NotFoundException {
        final Flight flight = flightRepository.findFlightByFlightCode(flightCode)
                .orElseThrow(() -> new NotFoundException("Flight not found"));
        return FlightMapper.toDto(flight);
    }

    @Override
    @Transactional
    public FlightDto createFlight(String flightCode, Integer numberOfSeats) {
        Optional<Flight> flightJpa = flightRepository.findFlightByFlightCode(flightCode);
        if(flightJpa.isPresent()) return FlightMapper.toDto(flightJpa.get());

        final Flight flight = Flight.builder()
                .flightCode(flightCode)
                .build();

        final List<Seat> seats = IntStream.range(1, numberOfSeats + 1)
                .boxed()
                .map(number -> Seat.builder()
                        .flight(flight)
                        .seatCode("A" + number)
                        .available(true)
                        .build()).toList();
        flight.setSeats(seats);

        flightRepository.save(flight);
        return FlightMapper.toDto(flight);
    }

    @Override
    public FlightDto getSeatsAvailable(String flightCode) throws NotFoundException {
        FlightDto flightDto = findFlightByFlightCode(flightCode);
        final List<SeatDto> seatsAvailable = seatService.getSeatsAvailable(flightDto.getFlightId());
        flightDto.setSeats(seatsAvailable);
        return flightDto;
    }
}
