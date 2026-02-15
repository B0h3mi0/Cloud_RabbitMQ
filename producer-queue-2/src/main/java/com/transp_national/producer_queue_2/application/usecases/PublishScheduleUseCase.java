package com.transp_national.producer_queue_2.application.usecases;

import com.transp_national.producer_queue_2.application.ports.out.SchedulePublisherPort;
import com.transp_national.producer_queue_2.domain.model.Schedule;
import org.springframework.stereotype.Service;

@Service
public class PublishScheduleUseCase {
    private final SchedulePublisherPort publisherPort;

    public PublishScheduleUseCase(SchedulePublisherPort publisherPort) {
        this.publisherPort = publisherPort;
    }

    public void execute(Schedule schedule) {
        publisherPort.publishSchedule(schedule);
    }
}