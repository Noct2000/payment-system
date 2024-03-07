package com.example.data.api.mapper;

import com.example.data.api.dto.request.UpdateBankTransactionRequestDto;
import com.example.data.api.dto.response.BankTransactionResponseDto;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.model.Payment;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionMapper {

    public BankTransaction toModelFromPayment(Payment payment) {
        return new BankTransaction()
                .setCreationTime(LocalDateTime.now())
                .setPayment(payment)
                .setAmount(payment.getAmount())
                .setStatus(BankTransaction.Status.ACTIVE);
    }

    public BankTransactionResponseDto toResponseDto(BankTransaction bankTransaction) {
        return new BankTransactionResponseDto()
                .setId(bankTransaction.getId())
                .setCreationTime(bankTransaction.getCreationTime())
                .setPaymentId(bankTransaction.getPayment().getId())
                .setAmount(bankTransaction.getAmount())
                .setStatus(bankTransaction.getStatus());
    }

    public BankTransaction toModelFromUpdateDto(
            UpdateBankTransactionRequestDto updateBankTransactionRequestDto,
            Payment payment
    ) {
        return toModelFromPayment(payment)
                .setId(updateBankTransactionRequestDto.getId())
                .setCreationTime(updateBankTransactionRequestDto.getCreationTime())
                .setPayment(payment)
                .setAmount(updateBankTransactionRequestDto.getAmount())
                .setStatus(updateBankTransactionRequestDto.getStatus());
    }
}
