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
    public static final String DLX_EXCHANGE = "horarios.dlx";

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_QUEUE, true);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(MAIN_QUEUE);
    }

    @Bean
    public Queue mainQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", MAIN_QUEUE);

        return new Queue(MAIN_QUEUE, true, false, false, args);
    }
}