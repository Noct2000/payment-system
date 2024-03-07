package com.example.data.api.controller;

import com.example.data.api.dto.request.CreateBankTransactionRequestDto;
import com.example.data.api.dto.request.UpdateBankTransactionRequestDto;
import com.example.data.api.dto.response.BankTransactionResponseDto;
import com.example.data.api.mapper.BankTransactionMapper;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.service.BankTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class BankTransactionController {
    private final BankTransactionService bankTransactionService;
    private final BankTransactionMapper bankTransactionMapper;

    @PostMapping
    public BankTransactionResponseDto create(
            @RequestBody
            @Valid
            CreateBankTransactionRequestDto createBankTransactionRequestDto
    ) {
        BankTransaction bankTransaction = bankTransactionService.createWithDebitingByPaymentId(
                createBankTransactionRequestDto.getPaymentId()
        );
        return bankTransactionMapper.toResponseDto(bankTransaction);
    }

    @PutMapping
    public BankTransactionResponseDto update(
            @RequestBody
            @Valid
            UpdateBankTransactionRequestDto updateBankTransactionRequestDto
    ) {
        BankTransaction bankTransaction = bankTransactionService
                .updateByRequestDto(updateBankTransactionRequestDto);
        return bankTransactionMapper.toResponseDto(bankTransaction);
    }
}
