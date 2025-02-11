package es.vortech.demo.service;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.exception.ConstraintException;
import es.vortech.demo.exception.NotFoundException;

public interface ReservationService {
    FlightDto getConfirmedReservations(String flightCode) throws NotFoundException;
    void createReservation(String flightCode, String seatCode) throws ConstraintException, NotFoundException;
    void cancelAllReservationsByFlightCode(String flightCode) throws NotFoundException;
    void cancelReservation(Long reservationId) throws NotFoundException;
    void processReservation(RequestDto requestDto);
    void doSendRejectedReservationMessage(RequestDto requestDto);
}
