package com.example.business.api.controller;

import com.example.business.api.dto.request.CreateBankTransactionRequestDto;
import com.example.business.api.dto.response.BankTransactionResponseDto;
import com.example.business.api.service.BankTransactionFeignClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class BankTransactionController {
    private final BankTransactionFeignClientService bankTransactionFeignClientService;

    @GetMapping("/payments/{payment_id}")
    public List<BankTransactionResponseDto> getByPaymentId(
            @PathVariable(name = "payment_id")
            Long id) {
        return bankTransactionFeignClientService.getByPaymentId(id);
    }

    @PostMapping
    public BankTransactionResponseDto create(
            @RequestBody
            CreateBankTransactionRequestDto createBankTransactionRequestDto
    ) {
        return bankTransactionFeignClientService.create(createBankTransactionRequestDto);
    }
}
