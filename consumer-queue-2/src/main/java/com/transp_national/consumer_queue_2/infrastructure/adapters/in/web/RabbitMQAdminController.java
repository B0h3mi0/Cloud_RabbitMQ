package com.transp_national.consumer_queue_2.infrastructure.adapters.in.web;


import com.transp_national.consumer_queue_2.application.dto.BindingDTO;
import com.transp_national.consumer_queue_2.application.service.AdminRabbitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rabbit-admin") // Le agregué api/v1 para mantener el estándar REST de tu proyecto
public class RabbitMQAdminController {

    private final AdminRabbitService service;

    public RabbitMQAdminController(AdminRabbitService service) {
        this.service = service;
    }

    @PostMapping("/colas/{nombrecola}")
    public String crearCola(@PathVariable String nombrecola) {
        service.crearCola(nombrecola);
        return "✅ Cola creada exitosamente: " + nombrecola;
    }

    @PostMapping("/exchanges/{nombreexchange}")
    public String crearExchange(@PathVariable String nombreexchange) {
        service.crearExchange(nombreexchange);
        return "Exchange creado exitosamente: " + nombreexchange;
    }

    @PostMapping("/bindings")
    public String crearBinding(@RequestBody BindingDTO request) {
        // Si te da error este método en el service, asegúrate de haberlo agregado en AdminRabbitService
        service.crearBinding(request);
        return "Binding creado entre la cola: " + request.getNombreCola() + " y el exchange: " + request.getNombreExchange();
    }

    @DeleteMapping("/colas/{nombrecola}")
    public String eliminarCola(@PathVariable String nombrecola) {
        service.eliminarCola(nombrecola);
        return "Cola eliminada: " + nombrecola;
    }

    @DeleteMapping("/exchanges/{nombreexchange}")
    public String eliminarExchange(@PathVariable String nombreexchange) {
        service.eliminarExchange(nombreexchange);
        return "Exchange eliminado: " + nombreexchange;
    }
}