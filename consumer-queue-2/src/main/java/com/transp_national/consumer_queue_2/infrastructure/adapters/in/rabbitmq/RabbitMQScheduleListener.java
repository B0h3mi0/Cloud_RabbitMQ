package com.transp_national.consumer_queue_2.infrastructure.adapters.in.rabbitmq;

import com.transp_national.consumer_queue_2.application.usecases.ProcessScheduleUseCase;
import com.transp_national.consumer_queue_2.domain.model.Schedule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQScheduleListener {

    private final ProcessScheduleUseCase useCase;

    public RabbitMQScheduleListener(ProcessScheduleUseCase useCase) {
        this.useCase = useCase;
    }

    // Escuchamos la SEGUNDA cola
    @RabbitListener(queues = "horarios.queue")
    public void receiveMessage(Schedule schedule) {
        useCase.execute(schedule);
    }
}
