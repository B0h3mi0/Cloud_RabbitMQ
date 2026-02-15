package com.transp_national.consumer_queue_2.application.ports.out;
import com.transp_national.consumer_queue_2.domain.model.Schedule;

public interface SaveScheduleFilePort {
    void saveToFile(Schedule schedule);
}