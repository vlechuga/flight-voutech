package es.vortech.demo.notification;


import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.dto.ReservationDto;
import es.vortech.demo.dto.SeatDto;

public interface ConsumerService {
    void doReceiveRejectedReservation(RequestDto message);
    void doReceiveConfirmedReservation(ReservationDto message);
    void doReceiveReservationRequest(RequestDto requestDto);
}
