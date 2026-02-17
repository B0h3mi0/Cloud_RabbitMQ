package com.transp_national.consumer_queue_2.infrastructure.adapters.in.web;

import com.transp_national.consumer_queue_2.application.service.RabbitListenerControlService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rabbit-listener")
public class RabbitListenerAdminController {

    private final RabbitListenerControlService service;

    public RabbitListenerAdminController(RabbitListenerControlService service) {
        this.service = service;
    }

    @PostMapping("/pausar/{id}")
    public String pausar(@PathVariable String id) {
        service.pausarListener(id);
        return "Consumidor pausado: " + id;
    }

    @PostMapping("/reanudar/{id}")
    public String reanudar(@PathVariable String id) {
        service.reanudarListener(id);
        return "Consumidor reanudado: " + id;
    }

    @GetMapping("/status/{id}")
    public String status(@PathVariable String id) {
        boolean isRunning = service.isListenerRunning(id);
        return "El consumidor [" + id + "] está " + (isRunning ? "ACTIVO 🟢" : "PAUSADO 🔴");
    }
}