package com.example.data.api.service.impl;

import com.example.data.api.exception.DebitException;
import com.example.data.api.model.BankAccount;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.repository.BankTransactionRepository;
import com.example.data.api.service.BankAccountService;
import com.example.data.api.service.BankTransactionService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankTransactionServiceImpl implements BankTransactionService {
    private final BankTransactionRepository bankTransactionRepository;
    private final BankAccountService bankAccountService;

    @Override
    public List<BankTransaction> findAllByPaymentId(Long paymentId) {
        return bankTransactionRepository.findAllByPaymentId(paymentId);
    }

    @Override
    @Transactional
    public BankTransaction createWithDebiting(BankTransaction bankTransaction) {
        BankAccount payer = bankTransaction.getPayment().getPayer();
        BankAccount recipient = bankTransaction.getPayment().getRecipient();
        BigDecimal restPayerAmount = payer.getAmount().subtract(bankTransaction.getAmount());
        if (restPayerAmount.signum() < 0) {
            throw new DebitException(
                    "Not enough money in account with id: "
                            + payer.getId()
                            + "; required: " + bankTransaction.getAmount()
                            + ", actual: " + payer.getAmount()
                    );
        }
        payer.setAmount(restPayerAmount);
        recipient.setAmount(recipient.getAmount().add(bankTransaction.getAmount()));
        bankAccountService.save(payer);
        bankAccountService.save(recipient);
        BankTransaction persistedBankTransaction = save(bankTransaction);
        return bankTransaction;
    }

    @Override
    public List<BankTransaction> findAll() {
        return bankTransactionRepository.findAll();
    }

    @Override
    public BankTransaction save(BankTransaction entity) {
        return bankTransactionRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        bankTransactionRepository.deleteById(id);
    }

    @Override
    public BankTransaction getById(Long id) {
        return bankTransactionRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("No transaction with id " + id)
                );
    }

    @Override
    @Transactional
    public BankTransaction update(BankTransaction entity) {
        boolean isExists = bankTransactionRepository.existsById(entity.getId());
        if (!isExists) {
            throw new EntityNotFoundException("Transaction with id "
                    + entity.getId()
                    + " is not exists");
        }
        return bankTransactionRepository.save(entity);
    }
}
