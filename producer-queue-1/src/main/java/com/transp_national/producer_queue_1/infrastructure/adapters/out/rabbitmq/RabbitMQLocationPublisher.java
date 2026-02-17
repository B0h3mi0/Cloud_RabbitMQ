package com.transp_national.producer_queue_1.infrastructure.adapters.out.rabbitmq;

import com.transp_national.producer_queue_1.application.ports.out.LocationPublisherPort;
import com.transp_national.producer_queue_1.domain.model.Location;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQLocationPublisher implements LocationPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQLocationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishLocation(Location location) {
        rabbitTemplate.convertAndSend(
                com.transp_national.producer_queue_2.infrastructure.config.RabbitMQConfig.EXCHANGE_NAME,
                com.transp_national.producer_queue_2.infrastructure.config.RabbitMQConfig.ROUTING_KEY,
                location
        );
        System.out.println(" [x] Sent location for vehicle: " + location.vehicleId());
    }
}