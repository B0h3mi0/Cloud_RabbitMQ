package com.transp_national.producer_queue_2.infrastructure.adapters.out.rabbitmq;
import com.transp_national.producer_queue_2.application.ports.out.SchedulePublisherPort;
import com.transp_national.producer_queue_2.domain.model.Schedule;
import com.transp_national.producer_queue_2.infrastructure.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSchedulePublisher implements SchedulePublisherPort {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSchedulePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishSchedule(Schedule schedule) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, schedule);
        System.out.println(" [x] Sent schedule update for route: " + schedule.routeId());
    }
}
