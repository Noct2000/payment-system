package com.example.data.api.mapper;

import com.example.data.api.dto.request.CreatePaymentRequestDto;
import com.example.data.api.dto.request.UpdatePaymentRequestDto;
import com.example.data.api.dto.response.PaymentResponseDto;
import com.example.data.api.model.BankAccount;
import com.example.data.api.model.Payment;
import com.example.data.api.service.BankAccountService;
import com.example.data.api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    private final BankAccountService bankAccountService;
    private final PaymentService paymentService;

    public PaymentResponseDto toResponseDto(Payment payment) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(payment.getId())
                .setPayerFullName(payment.getPayer().getFullName())
                .setInn(payment.getInn())
                .setPayerAccountNumber(payment.getPayer().getAccountNumber())
                .setRecipientAccountNumber(payment.getRecipient().getAccountNumber())
                .setBic(payment.getBic())
                .setOkpo(payment.getOkpo())
                .setRecipientFullName(payment.getRecipient().getFullName())
                .setMinsBeforeDebiting(payment.getMinsBeforeDebiting())
                .setAmount(payment.getAmount());
        return paymentResponseDto;
    }

    public Payment toModel(CreatePaymentRequestDto createPaymentRequestDto) {
        Payment payment = new Payment();
        BankAccount payer = bankAccountService.getById(createPaymentRequestDto.getPayerId());
        BankAccount recipient = bankAccountService
                .getById(createPaymentRequestDto.getRecipientId());
        payment.setInn(createPaymentRequestDto.getInn())
                .setPayer(payer)
                .setRecipient(recipient)
                .setBic(createPaymentRequestDto.getBic())
                .setOkpo(createPaymentRequestDto.getOkpo())
                .setMinsBeforeDebiting(createPaymentRequestDto.getMinsBeforeDebiting())
                .setAmount(createPaymentRequestDto.getAmount());
        return payment;
    }

    public Payment toModel(UpdatePaymentRequestDto updatePaymentRequestDto) {
        Payment payment = paymentService.getById(updatePaymentRequestDto.getId());
        BankAccount payer;
        BankAccount recipient;
        if (!payment.getPayer().getId().equals(updatePaymentRequestDto.getPayerId())) {
            payer = bankAccountService.getById(updatePaymentRequestDto.getPayerId());
        } else {
            payer = payment.getPayer();
        }
        if (!payment.getRecipient().getId().equals(updatePaymentRequestDto.getRecipientId())) {
            recipient = bankAccountService.getById(updatePaymentRequestDto.getRecipientId());
        } else {
            recipient = payment.getRecipient();
        }
        payment.setInn(updatePaymentRequestDto.getInn())
                .setPayer(payer)
                .setRecipient(recipient)
                .setBic(updatePaymentRequestDto.getBic())
                .setOkpo(updatePaymentRequestDto.getOkpo())
                .setMinsBeforeDebiting(updatePaymentRequestDto.getMinsBeforeDebiting())
                .setAmount(updatePaymentRequestDto.getAmount());
        return payment;
    }
}
