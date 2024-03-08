package com.example.cronapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBankTransactionRequestDto {
    @JsonProperty("payment_id")
    private Long paymentId;
}
