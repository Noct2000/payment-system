package com.example.cronapp.scheduled;

import com.example.cronapp.service.DebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final DebitService debitService;

    @Scheduled(fixedDelay = 60000)
    public void checkPayments() {
        debitService.createTransactionForPaymentsToDebiting();
    }
}
