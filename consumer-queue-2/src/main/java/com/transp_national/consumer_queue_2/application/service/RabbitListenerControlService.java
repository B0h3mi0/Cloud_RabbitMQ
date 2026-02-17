package com.transp_national.consumer_queue_2.application.service;

import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
public class RabbitListenerControlService {
    private final RabbitListenerEndpointRegistry registry;

    public RabbitListenerControlService(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    public void pausarListener(String id) {
        MessageListenerContainer listener = registry.getListenerContainer(id);
        if (listener != null && listener.isRunning()) {
            listener.stop();
        }
    }

    public void reanudarListener(String id) {
        MessageListenerContainer listener = registry.getListenerContainer(id);
        if (listener != null && !listener.isRunning()) {
            listener.start();
        }
    }

    public boolean isListenerRunning(String id) {
        MessageListenerContainer listener = registry.getListenerContainer(id);
        return listener != null && listener.isRunning();
    }
}