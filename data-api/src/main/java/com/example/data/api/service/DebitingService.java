package com.example.data.api.service;

import com.example.data.api.model.Payment;

public interface DebitingService {
    Payment createPaymentWithFirstTransactionAndDebiting(Payment payment);
}
