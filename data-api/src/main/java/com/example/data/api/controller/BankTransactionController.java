package com.example.data.api.controller;

import com.example.data.api.dto.request.CreateBankTransactionRequestDto;
import com.example.data.api.dto.response.BankTransactionResponseDto;
import com.example.data.api.mapper.BankTransactionMapper;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.service.BankTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
            CreateBankTransactionRequestDto createBankTransactionRequestDto
    ) {
        BankTransaction bankTransaction = bankTransactionService.createWithDebitingByPaymentId(
                createBankTransactionRequestDto.getPaymentId()
        );
        return bankTransactionMapper.toResponseDto(bankTransaction);
    }
}
