package com.example.data.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreatePaymentRequestDto {
    @Pattern(regexp = "^\\d{12}$")
    private String inn;
    @NotNull
    @JsonProperty("payer_id")
    private Long payerId;
    @NotNull
    @JsonProperty("recipient_id")
    private Long recipientId;
    @Pattern(regexp = "^\\d{6}$")
    private String bic;
    @Pattern(regexp = "^\\d{10}$")
    private String okpo;
    @Min(1)
    @JsonProperty("mins_before_debiting")
    private Long minsBeforeDebiting;
    @Min(1)
    private BigDecimal amount;
}
