package com.transp_national.producer_queue_2.domain.model;

import java.time.LocalDateTime;

public record Schedule(
        String routeId,
        String status, // ej: "DESVIO", "ATRASO", "NORMAL"
        String updatedTime,
        String description
) {}