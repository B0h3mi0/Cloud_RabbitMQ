package com.transp_national.producer_queue_1.application.ports.out;

import com.transp_national.producer_queue_1.domain.model.Location;

public interface LocationPublisherPort {
    void publishLocation(Location location);
}