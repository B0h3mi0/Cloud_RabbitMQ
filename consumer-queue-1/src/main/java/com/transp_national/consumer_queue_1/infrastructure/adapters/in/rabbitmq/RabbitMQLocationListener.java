package com.transp_national.consumer_queue_1.infrastructure.adapters.in.rabbitmq;

import com.transp_national.consumer_queue_1.application.usecases.ProcessLocationUseCase;
import com.transp_national.consumer_queue_1.domain.model.Location;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQLocationListener {
    private final ProcessLocationUseCase useCase;

    public RabbitMQLocationListener(ProcessLocationUseCase useCase) {
        this.useCase = useCase;
    }

    @RabbitListener(queues = "ubicaciones.queue")
    public void receiveMessage(Location location) {
        useCase.execute(location);
    }
}