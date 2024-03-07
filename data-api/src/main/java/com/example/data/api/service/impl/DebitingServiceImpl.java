package com.example.data.api.service.impl;

import com.example.data.api.mapper.BankTransactionMapper;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.model.Payment;
import com.example.data.api.service.BankTransactionService;
import com.example.data.api.service.DebitingService;
import com.example.data.api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DebitingServiceImpl implements DebitingService {
    private final BankTransactionService bankTransactionService;
    private final PaymentService paymentService;
    private final BankTransactionMapper bankTransactionMapper;

    @Override
    @Transactional
    public Payment createPaymentWithFirstTransactionAndDebiting(Payment payment) {
        Payment persistedPayment = paymentService.save(payment);
        BankTransaction bankTransaction = bankTransactionMapper.toModelFromPayment(payment);
        bankTransactionService.createWithDebiting(bankTransaction);
        return persistedPayment;
    }
}
