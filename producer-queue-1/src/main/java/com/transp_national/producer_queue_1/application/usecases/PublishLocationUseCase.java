package com.transp_national.producer_queue_1.application.usecases;

import com.transp_national.producer_queue_1.application.ports.out.LocationPublisherPort;
import com.transp_national.producer_queue_1.domain.model.Location;
import org.springframework.stereotype.Service;

@Service
public class PublishLocationUseCase {

    private final LocationPublisherPort publisherPort;

    public PublishLocationUseCase(LocationPublisherPort publisherPort) {
        this.publisherPort = publisherPort;
    }

    public void execute(Location location) {
        // Aquí iría la lógica de negocio, validaciones, etc., antes de enviar
        publisherPort.publishLocation(location);
    }
}