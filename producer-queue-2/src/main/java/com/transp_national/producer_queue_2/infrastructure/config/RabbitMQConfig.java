package com.transp_national.producer_queue_2.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // Definimos la SEGUNDA cola
    public static final String QUEUE_NAME = "horarios.queue";
    public static final String EXCHANGE_NAME = "horarios.exchange";
    public static final String ROUTING_KEY = "horarios.routing.key";

    @Bean
    public Queue queue() { return new Queue(QUEUE_NAME, true); }

    @Bean
    public DirectExchange exchange() { return new DirectExchange(EXCHANGE_NAME); }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @SuppressWarnings("removal")
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }
}