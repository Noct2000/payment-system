package com.example.data.api.service;

import com.example.data.api.model.Payment;
import java.util.List;

public interface PaymentService extends CrudService<Payment> {
    List<Payment> findAllByInn(String inn);

    List<Payment> findAllByOkpo(String okpo);

    Payment createWithFirstTransaction(Payment payment);
}
