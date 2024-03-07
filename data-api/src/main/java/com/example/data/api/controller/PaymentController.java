package com.example.data.api.controller;

import com.example.data.api.dto.request.CreatePaymentRequestDto;
import com.example.data.api.dto.request.UpdatePaymentRequestDto;
import com.example.data.api.dto.response.PaymentResponseDto;
import com.example.data.api.mapper.PaymentMapper;
import com.example.data.api.model.Payment;
import com.example.data.api.service.DebitingService;
import com.example.data.api.service.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final DebitingService debitingService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public PaymentResponseDto create(
            @RequestBody @Valid CreatePaymentRequestDto createPaymentRequestDto
    ) {
        Payment payment = debitingService.createPaymentWithFirstTransactionAndDebiting(
                paymentMapper.toModel(createPaymentRequestDto)
        );
        return paymentMapper.toResponseDto(payment);
    }

    @GetMapping("/{id}")
    public PaymentResponseDto getById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        return paymentMapper.toResponseDto(payment);
    }

    @GetMapping("/by-inn/{inn}")
    public List<PaymentResponseDto> getByInn(@PathVariable String inn) {
        List<Payment> payments = paymentService.findAllByInn(inn);
        return payments.stream().map(paymentMapper::toResponseDto).toList();
    }

    @GetMapping("/by-okpo/{okpo}")
    public List<PaymentResponseDto> getByOkpo(@PathVariable String okpo) {
        List<Payment> payments = paymentService.findAllByOkpo(okpo);
        return payments.stream().map(paymentMapper::toResponseDto).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping
    public PaymentResponseDto update(
            @RequestBody @Valid UpdatePaymentRequestDto updatePaymentRequestDto
    ) {
        Payment payment = paymentService.update(paymentMapper.toModel(updatePaymentRequestDto));
        return paymentMapper.toResponseDto(payment);
    }
}
