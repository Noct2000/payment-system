package com.example.data.api.service.impl;

import com.example.data.api.model.BankTransaction;
import com.example.data.api.repository.BankTransactionRepository;
import com.example.data.api.service.BankTransactionService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankTransactionServiceImpl implements BankTransactionService {
    private final BankTransactionRepository bankTransactionRepository;

    @Override
    public List<BankTransaction> findAllByPaymentId(Long paymentId) {
        return bankTransactionRepository.findAllByPaymentId(paymentId);
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
