package com.transp_national.consumer_queue_1.application.ports.out;


import com.transp_national.consumer_queue_1.domain.model.Location;

public interface SaveLocationPort {
    void save(Location location);
}