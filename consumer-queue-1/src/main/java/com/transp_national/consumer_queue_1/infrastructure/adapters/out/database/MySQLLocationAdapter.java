package com.transp_national.consumer_queue_1.infrastructure.adapters.out.database;

import com.transp_national.consumer_queue_1.application.ports.out.SaveLocationPort;
import com.transp_national.consumer_queue_1.domain.model.Location;
import com.transp_national.consumer_queue_1.infrastructure.adapters.out.database.entity.LocationEntity;
import com.transp_national.consumer_queue_1.infrastructure.adapters.out.database.repository.JpaLocationRepository;
import org.springframework.stereotype.Component;

@Component
public class MySQLLocationAdapter implements SaveLocationPort {
    private final JpaLocationRepository repository;

    public MySQLLocationAdapter(JpaLocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Location location) {
        LocationEntity entity = new LocationEntity(
                location.vehicleId(), location.latitude(), location.longitude(), location.timestamp()
        );
        repository.save(entity);
        System.out.println("Ubicación guardada en MySQL exitosamente.");
    }
}