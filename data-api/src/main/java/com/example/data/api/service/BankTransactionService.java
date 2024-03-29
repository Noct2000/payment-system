package com.example.data.api.service;

import com.example.data.api.dto.request.UpdateBankTransactionRequestDto;
import com.example.data.api.model.BankTransaction;
import java.util.List;

public interface BankTransactionService extends CrudService<BankTransaction> {
    List<BankTransaction> findAllByPaymentId(Long paymentId);

    BankTransaction createWithDebiting(BankTransaction bankTransaction);

    BankTransaction createWithDebitingByPaymentId(Long paymentId);

    BankTransaction updateByRequestDto(
            UpdateBankTransactionRequestDto updateBankTransactionRequestDto
    );

    BankTransaction rollbackById(Long id);

    List<BankTransaction> getLastWithUniquePaymentId();
}
