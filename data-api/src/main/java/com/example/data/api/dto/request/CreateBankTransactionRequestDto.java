package com.example.data.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateBankTransactionRequestDto {
    @JsonProperty("payment_id")
    private Long paymentId;
}
