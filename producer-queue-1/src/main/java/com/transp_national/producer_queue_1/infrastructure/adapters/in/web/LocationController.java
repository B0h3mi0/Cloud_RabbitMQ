package com.transp_national.producer_queue_1.infrastructure.adapters.in.web;

import com.transp_national.producer_queue_1.application.usecases.PublishLocationUseCase;
import com.transp_national.producer_queue_1.domain.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final PublishLocationUseCase useCase;

    public LocationController(PublishLocationUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<String> receiveLocation(@RequestBody Location location) {
        Location locationToPublish = location.timestamp() != null ? location
                : new Location(location.vehicleId(), location.latitude(), location.longitude(), LocalDateTime.now());

        useCase.execute(locationToPublish);
        return ResponseEntity.accepted().body("Ubicación recibida y encolada.");
    }
}
