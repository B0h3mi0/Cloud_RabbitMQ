package com.transp_national.producer_queue_2.infrastructure.adapters.in.web;

import com.transp_national.producer_queue_2.application.usecases.PublishScheduleUseCase;
import com.transp_national.producer_queue_2.domain.model.Schedule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final PublishScheduleUseCase useCase;

    public ScheduleController(PublishScheduleUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<String> receiveScheduleUpdate(@RequestBody Schedule schedule) {
        useCase.execute(schedule);
        return ResponseEntity.accepted().body("Actualización de horario recibida y encolada.");
    }
}