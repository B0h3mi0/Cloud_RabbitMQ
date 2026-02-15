package com.transp_national.producer_queue_1.domain.model;

import java.time.LocalDateTime;

public record Location(
        String vehicleId,
        Double latitude,
        Double longitude,
        LocalDateTime timestamp
) {}