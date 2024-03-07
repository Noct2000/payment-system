package com.example.data.api.service.impl;

import com.example.data.api.model.BankAccount;
import com.example.data.api.repository.BankAccountRepository;
import com.example.data.api.service.BankAccountService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount save(BankAccount entity) {
        return bankAccountRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        bankAccountRepository.deleteById(id);
    }

    @Override
    public BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("No bank account with id " + id)
                );
    }

    @Override
    @Transactional
    public BankAccount update(BankAccount entity) {
        boolean isExists = bankAccountRepository.existsById(entity.getId());
        if (!isExists) {
            throw new EntityNotFoundException("Bank account with id "
                    + entity.getId()
                    + " is not exists");
        }

        return bankAccountRepository.save(entity);
    }
}
