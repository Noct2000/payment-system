package com.example.business.api.service.impl;

import com.example.business.api.dto.response.PaymentResponseDto;
import com.example.business.api.service.BankTransactionFeignClientService;
import com.example.business.api.service.PaymentFeignClientService;
import com.example.business.api.service.PaymentService;
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
        return bankTransactionFeignClientService.getLastWithUniquePaymentId().stream()
                .map(
                        bankTransaction -> paymentFeignClientService
                                    .getById(bankTransaction.getPaymentId())
                )
                .toList();
    }
}
