package com.example.data.api.service.impl;

import com.example.data.api.dto.request.UpdateBankTransactionRequestDto;
import com.example.data.api.exception.BankTransactionStatusException;
import com.example.data.api.exception.DebitException;
import com.example.data.api.mapper.BankTransactionMapper;
import com.example.data.api.model.BankAccount;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.model.Payment;
import com.example.data.api.repository.BankTransactionRepository;
import com.example.data.api.service.BankAccountService;
import com.example.data.api.service.BankTransactionService;
import com.example.data.api.service.PaymentService;
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
    private final BankTransactionMapper bankTransactionMapper;
    private final PaymentService paymentService;

    @Override
    public List<BankTransaction> findAllByPaymentId(Long paymentId) {
        return bankTransactionRepository.findAllByPaymentId(paymentId);
    }

    @Override
    @Transactional
    public BankTransaction createWithDebiting(BankTransaction bankTransaction) {
        BankAccount payer = bankTransaction.getPayment().getPayer();
        BankAccount recipient = bankTransaction.getPayment().getRecipient();
        transferMoney(bankTransaction, payer, recipient);
        save(bankTransaction);
        return bankTransaction;
    }

    @Override
    @Transactional
    public BankTransaction createWithDebitingByPaymentId(Long paymentId) {
        Payment payment = paymentService.getById(paymentId);
        BankTransaction bankTransaction = bankTransactionMapper.toModelFromPayment(payment);
        return createWithDebiting(bankTransaction);
    }

    @Override
    @Transactional
    public BankTransaction updateByRequestDto(
            UpdateBankTransactionRequestDto updateBankTransactionRequestDto
    ) {
        Payment payment = paymentService.getById(
                updateBankTransactionRequestDto.getPaymentId()
        );
        BankTransaction bankTransaction = bankTransactionMapper
                .toModelFromUpdateDto(updateBankTransactionRequestDto, payment);
        return update(bankTransaction);
    }

    @Override
    @Transactional
    public BankTransaction rollbackById(Long id) {
        BankTransaction bankTransaction = getById(id);
        BankAccount payer = bankTransaction.getPayment().getPayer();
        BankAccount recipient = bankTransaction.getPayment().getRecipient();
        if (bankTransaction.getStatus() == BankTransaction.Status.CANCELLED) {
            throw new BankTransactionStatusException(
                    "Transaction with id "
                            + id + " already canceled"
            );
        }
        bankTransaction.setStatus(BankTransaction.Status.CANCELLED);
        transferMoney(bankTransaction, recipient, payer);
        save(bankTransaction);
        return bankTransaction;
    }

    @Override
    public List<BankTransaction> getLastWithUniquePaymentId() {
        return bankTransactionRepository.getLastWithUniquePaymentId();
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
        bankTransactionRepository.save(entity);
        return entity;
    }

    private void transferMoney(
            BankTransaction bankTransaction,
            BankAccount payer,
            BankAccount recipient
    ) {
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
    }
}
