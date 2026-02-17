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
    public static final String DLX_EXCHANGE = "ubicaciones.dlx";

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
        // Magia: Si un mensaje muere aquí, envíalo al DLX
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", MAIN_QUEUE);

        return new Queue(MAIN_QUEUE, true, false, false, args);
    }
}
