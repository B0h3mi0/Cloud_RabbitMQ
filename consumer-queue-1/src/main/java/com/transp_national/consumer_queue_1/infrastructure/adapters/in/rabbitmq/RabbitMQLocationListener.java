package com.transp_national.consumer_queue_1.infrastructure.adapters.in.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQLocationListener {

    @RabbitListener(id = "listener-ubicaciones", queues = "ubicaciones.queue", ackMode = "MANUAL")
    public void recibirUbicacion(Message mensaje, Channel canal) throws IOException {

        long deliveryTag = mensaje.getMessageProperties().getDeliveryTag();

        try {
            String jsonBody = new String(mensaje.getBody());
            System.out.println(" Procesando ubicación: " + jsonBody);

            canal.basicAck(deliveryTag, false);
            System.out.println("Guardado en DB y ACK enviado.");

        } catch (Exception e) {
            System.err.println("Error al procesar (ej. MySQL caído): " + e.getMessage());
            System.out.println("NACK enviado. Mensaje movido a la DLQ.");
        }
    }
}
