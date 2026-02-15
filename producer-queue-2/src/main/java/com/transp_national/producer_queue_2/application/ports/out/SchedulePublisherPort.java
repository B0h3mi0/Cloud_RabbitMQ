package com.transp_national.producer_queue_2.application.ports.out;

import com.transp_national.producer_queue_2.domain.model.Schedule;

public interface SchedulePublisherPort {
    void publishSchedule(Schedule schedule);
}