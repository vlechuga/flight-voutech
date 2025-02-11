package es.vortech.demo.notification;

import es.vortech.demo.config.RabbitMQConfig;
import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Async
    public void doSendConfirmedReservation(ReservationDto reservationDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_CONFIRMED_RESERVATION, reservationDto);
    }

    @Override
    @Async
    public void doSendRejectedReservation(RequestDto requestDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_REJECTED_RESERVATION, requestDto);
    }

    @Override
    @Async
    public void doSendReservationRequest(RequestDto requestDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_RESERVATION, requestDto);
    }
}
