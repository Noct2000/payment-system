package com.example.data.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentResponseDto {
    private Long id;
    @JsonProperty("payer_full_name")
    private String payerFullName;
    private String inn;
    @JsonProperty("payer_account_number")
    private String payerAccountNumber;
    @JsonProperty("recipient_account_number")
    private String recipientAccountNumber;
    private String bic;
    private String okpo;
    @JsonProperty("recipient_full_name")
    private String recipientFullName;
    @JsonProperty("mins_before_debiting")
    private Long minsBeforeDebiting;
    private BigDecimal amount;
}
