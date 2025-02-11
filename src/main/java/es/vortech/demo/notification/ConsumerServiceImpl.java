package es.vortech.demo.notification;

import es.vortech.demo.config.RabbitMQConfig;
import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.dto.ReservationDto;
import es.vortech.demo.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ConsumerServiceImpl implements ConsumerService {

    private final ReservationService reservationService;

    @Override
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_REJECTED_RESERVATION})
    public void doReceiveRejectedReservation(@Payload RequestDto message) {
        log.info("************ REJECTED RESERVATION. SEND EMAIL ***********");
        log.info(message.toString());
    }

    @Override
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_CONFIRMED_RESERVATION})
    public void doReceiveConfirmedReservation(@Payload ReservationDto message) {
        log.info("************ CONFIRMED RESERVATION. SEND EMAIL ***********");
        log.info(message.toString());
    }

    @Override
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_RESERVATION})
    public void doReceiveReservationRequest(@Payload RequestDto requestDto) {
        log.info("************ PROCESSING RESERVATION ***********");
        try {
            reservationService.processReservation(requestDto);
        } catch (Exception ex) {
            log.info("************ ERROR RESERVATION ***********");
            requestDto.setMessage(ex.getMessage());
            reservationService.doSendRejectedReservationMessage(requestDto);
        }
    }

}
