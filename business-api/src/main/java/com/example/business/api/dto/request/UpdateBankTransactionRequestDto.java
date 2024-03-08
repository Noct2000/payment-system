package com.example.business.api.dto.request;

import com.example.business.api.dto.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateBankTransactionRequestDto {
    @Min(1)
    private Long id;
    @NotNull
    @JsonProperty("creation_time")
    private LocalDateTime creationTime;
    @Min(1)
    @JsonProperty("payment_id")
    private Long paymentId;
    @Min(0)
    private BigDecimal amount;
    @NotNull
    private Status status;
}
