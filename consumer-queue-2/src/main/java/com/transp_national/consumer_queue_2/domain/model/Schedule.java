package com.transp_national.consumer_queue_2.domain.model;


public record Schedule(
        String routeId,
        String status,
        String updatedTime,
        String description
) {}