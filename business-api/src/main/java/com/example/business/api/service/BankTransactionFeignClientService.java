package com.example.business.api.service;

import com.example.business.api.dto.request.CreateBankTransactionRequestDto;
import com.example.business.api.dto.response.BankTransactionResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "bank-transaction", url = "${data-api}")
public interface BankTransactionFeignClientService {
    @GetMapping("/transactions/payments/{payment_id}")
    List<BankTransactionResponseDto> getByPaymentId(
            @PathVariable(name = "payment_id")
            Long id
            );

    @PostMapping("/transactions")
    BankTransactionResponseDto create(
            @RequestBody
            CreateBankTransactionRequestDto createBankTransactionRequestDto
    );

    @GetMapping("/transactions/last-with-unique-payment-id")
    List<BankTransactionResponseDto> getLastWithUniquePaymentId();

    @RequestMapping(
            value = "/transactions/rollback/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    BankTransactionResponseDto rollback(@PathVariable Long id);
}
