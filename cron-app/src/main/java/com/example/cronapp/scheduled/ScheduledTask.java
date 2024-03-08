package com.example.cronapp.scheduled;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ScheduledTask {
    @Scheduled(fixedRate = 60000)
    public void checkPayments() {
        log.info("test");
    }
}
