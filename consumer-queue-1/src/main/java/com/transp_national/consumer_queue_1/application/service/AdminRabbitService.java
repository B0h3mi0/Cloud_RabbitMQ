package com.transp_national.consumer_queue_1.application.service;

import com.transp_national.consumer_queue_1.application.dto.BindingDTO;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
public class AdminRabbitService {
    private final AmqpAdmin amqpAdmin;

    public AdminRabbitService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public void crearCola(String nombreCola) {
        amqpAdmin.declareQueue(new Queue(nombreCola, true));
    }

    public void crearExchange(String nombreExchange) {
        amqpAdmin.declareExchange(new DirectExchange(nombreExchange));
    }

    public void eliminarCola(String nombreCola) {
        amqpAdmin.deleteQueue(nombreCola);
    }

    public void eliminarExchange(String nombreExchange) {
        amqpAdmin.deleteExchange(nombreExchange);
    }

    public void crearBinding(BindingDTO request) {

        Queue queue = new Queue(request.getNombreCola(), true);
        DirectExchange exchange = new DirectExchange(request.getNombreExchange());
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(request.getNombreCola());
        amqpAdmin.declareBinding(binding);
    }
}