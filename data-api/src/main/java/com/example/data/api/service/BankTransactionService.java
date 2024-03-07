package com.example.data.api.service;

import com.example.data.api.model.BankTransaction;
import java.util.List;

public interface BankTransactionService extends CrudService<BankTransaction> {
    List<BankTransaction> findAllByPaymentId(Long paymentId);
}
