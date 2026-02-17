package com.transp_national.consumer_queue_2.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // Cambiamos los nombres para el dominio de Horarios
    public static final String MAIN_QUEUE = "horarios.queue";
    public static final String DLQ_QUEUE = "horarios.dlq";


    @Bean
    public Queue ubicacionesQueue() {
        return new Queue("horarios.queue", true); // Sin argumentos adicionales
    }
}