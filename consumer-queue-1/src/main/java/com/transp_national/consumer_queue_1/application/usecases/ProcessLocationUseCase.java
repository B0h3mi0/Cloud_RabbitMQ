package com.transp_national.consumer_queue_1.application.usecases;

import com.transp_national.consumer_queue_1.application.ports.out.SaveLocationPort;
import com.transp_national.consumer_queue_1.domain.model.Location;
import org.springframework.stereotype.Service;

@Service
public class ProcessLocationUseCase {
    private final SaveLocationPort saveLocationPort;

    public ProcessLocationUseCase(SaveLocationPort saveLocationPort) {
        this.saveLocationPort = saveLocationPort;
    }

    public void execute(Location location) {
        System.out.println("Procesando nueva ubicación para el vehículo: " + location.vehicleId());
        saveLocationPort.save(location);
    }
}