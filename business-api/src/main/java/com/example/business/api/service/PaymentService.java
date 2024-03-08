package com.example.business.api.service;

import com.example.business.api.dto.response.PaymentResponseDto;
import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getPaymentsToTransaction();
}
