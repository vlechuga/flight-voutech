package es.vortech.demo.service;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.exception.NotFoundException;

public interface FlightService {
    FlightDto findFlightByFlightCode(String flightCode) throws NotFoundException;
    FlightDto createFlight(String flightCode, Integer numberOfSeats);
    FlightDto getSeatsAvailable(String flightCode) throws NotFoundException;
}
