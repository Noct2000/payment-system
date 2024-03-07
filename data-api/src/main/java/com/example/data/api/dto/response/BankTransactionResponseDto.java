package com.example.data.api.dto.response;

import com.example.data.api.model.BankTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BankTransactionResponseDto {
    private Long id;
    @JsonProperty("creation_time")
    private LocalDateTime creationTime;
    @JsonProperty("payment_id")
    private Long paymentId;
    private BigDecimal amount;
    private BankTransaction.Status status;
}
