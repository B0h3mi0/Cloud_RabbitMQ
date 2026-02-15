package com.transp_national.consumer_queue_1.infrastructure.adapters.out.database.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    // Getters, Setters y constructores (o usa @Data de Lombok si lo prefieres)
    public LocationEntity() {}
    public LocationEntity(String vehicleId, Double latitude, Double longitude, LocalDateTime timestamp) {
        this.vehicleId = vehicleId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }
}