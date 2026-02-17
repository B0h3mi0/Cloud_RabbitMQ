package com.transp_national.producer_queue_1.infrastructure.config; // Ajusta según el microservicio

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "ubicaciones.queue";
    public static final String EXCHANGE_NAME = "ubicaciones.exchange";
    public static final String ROUTING_KEY = "ubicaciones.routing.key";

    @Bean
    public Queue horariosQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange horariosExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding horariosBinding(Queue horariosQueue, DirectExchange horariosExchange) {
        return BindingBuilder.bind(horariosQueue).to(horariosExchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}