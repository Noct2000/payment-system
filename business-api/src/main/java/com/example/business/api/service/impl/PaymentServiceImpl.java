package com.example.business.api.service.impl;

import com.example.business.api.dto.response.BankTransactionResponseDto;
import com.example.business.api.dto.response.PaymentResponseDto;
import com.example.business.api.service.BankTransactionFeignClientService;
import com.example.business.api.service.PaymentFeignClientService;
import com.example.business.api.service.PaymentService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final BankTransactionFeignClientService bankTransactionFeignClientService;
    private final PaymentFeignClientService paymentFeignClientService;

    @Override
    public List<PaymentResponseDto> getPaymentsToTransaction() {
        List<BankTransactionResponseDto> lastTransactionsWithUniquePaymentId =
                bankTransactionFeignClientService.getLastWithUniquePaymentId();
        List<PaymentResponseDto> payments = new ArrayList<>();
        for (BankTransactionResponseDto transaction: lastTransactionsWithUniquePaymentId) {
            PaymentResponseDto payment = paymentFeignClientService
                    .getById(transaction.getPaymentId());
            LocalDateTime debitDateTime = transaction.getCreationTime()
                    .plusMinutes(payment.getMinsBeforeDebiting());
            LocalDateTime now = LocalDateTime.now();
            if (debitDateTime.isBefore(now) || debitDateTime.isEqual(now)) {
                payments.add(payment);
            }
        }
        return payments;
    }
}
