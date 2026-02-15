package com.transp_national.consumer_queue_2.application.usecases;

import com.transp_national.consumer_queue_2.application.ports.out.SaveScheduleFilePort;
import com.transp_national.consumer_queue_2.domain.model.Schedule;
import org.springframework.stereotype.Service;

@Service
public class ProcessScheduleUseCase {
    private final SaveScheduleFilePort filePort;

    public ProcessScheduleUseCase(SaveScheduleFilePort filePort) {
        this.filePort = filePort;
    }

    public void execute(Schedule schedule) {
        System.out.println("Procesando actualización de horario para la ruta: " + schedule.routeId());
        filePort.saveToFile(schedule);
    }
}