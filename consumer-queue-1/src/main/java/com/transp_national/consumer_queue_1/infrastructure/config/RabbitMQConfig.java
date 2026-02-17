package com.transp_national.consumer_queue_1.infrastructure.config;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String MAIN_QUEUE = "ubicaciones.queue";
    public static final String DLQ_QUEUE = "ubicaciones.dlq";

    @Bean
    public Queue ubicacionesQueue() {
        return new Queue("ubicaciones.queue", true); // Sin argumentos adicionales
    }
}
