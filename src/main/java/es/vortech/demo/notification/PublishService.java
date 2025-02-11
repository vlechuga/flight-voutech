package es.vortech.demo.notification;

import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.dto.ReservationDto;

public interface PublishService {
    void doSendConfirmedReservation(ReservationDto reservationDto);
    void doSendRejectedReservation(RequestDto requestDto);
    void doSendReservationRequest(RequestDto requestDto);
}
