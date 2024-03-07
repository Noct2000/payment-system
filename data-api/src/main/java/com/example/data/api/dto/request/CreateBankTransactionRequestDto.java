package com.example.data.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateBankTransactionRequestDto {
    @Min(1)
    @JsonProperty("payment_id")
    private Long paymentId;
}
