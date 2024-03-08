package com.example.business.api.service;

import com.example.business.api.dto.response.BankTransactionResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bank-transaction", url = "${data-api}")
public interface BankTransactionFeignClientService {
    @GetMapping("/transactions/payments/{payment_id}")
    List<BankTransactionResponseDto> getByPaymentId(
            @PathVariable(name = "payment_id")
            Long id
            );
}
