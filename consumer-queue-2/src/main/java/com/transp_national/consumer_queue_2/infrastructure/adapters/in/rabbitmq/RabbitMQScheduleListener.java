package com.transp_national.consumer_queue_2.infrastructure.adapters.in.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.transp_national.consumer_queue_2.application.usecases.ProcessScheduleUseCase;
import java.io.IOException;

@Component
public class RabbitMQScheduleListener {

    private final ProcessScheduleUseCase processScheduleUseCase;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public RabbitMQScheduleListener(ProcessScheduleUseCase processScheduleUseCase) {
        this.processScheduleUseCase = processScheduleUseCase;
        this.objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
    }

    // ID único para este consumidor
    @RabbitListener(id = "listener-horarios", queues = "horarios.queue", ackMode = "MANUAL")
    public void recibirHorario(org.springframework.amqp.core.Message mensaje, com.rabbitmq.client.Channel canal)
            throws java.io.IOException {

        long deliveryTag = mensaje.getMessageProperties().getDeliveryTag();

        try {
            String jsonBody = new String(mensaje.getBody());
            System.out.println("Procesando horario para subir a S3: " + jsonBody);

            com.transp_national.consumer_queue_2.domain.model.Schedule schedule = objectMapper.readValue(jsonBody,
                    com.transp_national.consumer_queue_2.domain.model.Schedule.class);
            processScheduleUseCase.execute(schedule);

            // Confirmación manual de éxito
            canal.basicAck(deliveryTag, false);
            System.out.println("Archivo subido a S3 y ACK enviado.");

        } catch (Exception e) {
            System.err.println("Error al procesar (ej. AWS S3 caído): " + e.getMessage());
            canal.basicNack(deliveryTag, false, false);
            System.out.println("NACK enviado. Mensaje movido a horarios.dlq.");
        }
    }
}