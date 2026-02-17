package com.transp_national.consumer_queue_2.infrastructure.adapters.in.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQScheduleListener {

    // ID único para este consumidor
    @RabbitListener(id = "listener-horarios", queues = "horarios.queue", ackMode = "MANUAL")
    public void recibirHorario(Message mensaje, Channel canal) throws IOException {

        long deliveryTag = mensaje.getMessageProperties().getDeliveryTag();

        try {
            String jsonBody = new String(mensaje.getBody());
            System.out.println("Procesando horario para subir a S3: " + jsonBody);

            // TODO: Llamar a tu caso de uso que sube el archivo a AWS S3

            // Confirmación manual de éxito
            canal.basicAck(deliveryTag, false);
            System.out.println("Archivo subido a S3 y ACK enviado.");

        } catch (Exception e) {
            System.err.println("Error al procesar (ej. AWS S3 caído): " + e.getMessage());
            System.out.println("NACK enviado. Mensaje movido a horarios.dlq.");
        }
    }
}

