package com.example.data.api.mapper;

import com.example.data.api.model.BankTransaction;
import com.example.data.api.model.Payment;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionMapper {
    public BankTransaction toNewModelFromPayment(Payment payment) {
        return new BankTransaction()
                .setCreationTime(LocalDateTime.now())
                .setPayment(payment)
                .setAmount(payment.getAmount())
                .setStatus(BankTransaction.Status.ACTIVE);
    }
}
