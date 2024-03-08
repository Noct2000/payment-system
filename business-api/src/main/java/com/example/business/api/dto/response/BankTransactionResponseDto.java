package com.example.business.api.dto.response;

import com.example.business.api.dto.Status;
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
    private Status status;
}
