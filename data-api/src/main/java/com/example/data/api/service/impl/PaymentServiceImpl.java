package com.example.data.api.service.impl;

import com.example.data.api.mapper.BankTransactionMapper;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.model.Payment;
import com.example.data.api.repository.PaymentRepository;
import com.example.data.api.service.BankTransactionService;
import com.example.data.api.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BankTransactionService bankTransactionService;
    private final BankTransactionMapper bankTransactionMapper;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment save(Payment entity) {
        return paymentRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("No payment with id " + id)
                );
    }

    @Override
    @Transactional
    public Payment update(Payment entity) {
        boolean isExists = paymentRepository.existsById(entity.getId());
        if (!isExists) {
            throw new EntityNotFoundException(
                    "Payment with id "
                            + entity.getId()
                            + " is not exists");
        }
        return paymentRepository.save(entity);
    }

    @Override
    public List<Payment> findAllByInn(String inn) {
        return paymentRepository.findAllByInn(inn);
    }

    @Override
    public List<Payment> findAllByOkpo(String okpo) {
        return paymentRepository.findAllByOkpo(okpo);
    }

    @Override
    @Transactional
    public Payment createWithFirstTransaction(Payment payment) {
        Payment persistedPayment = save(payment);
        BankTransaction bankTransaction = bankTransactionMapper.toNewModelFromPayment(payment);
        bankTransactionService.createWithDebiting(bankTransaction);
        return persistedPayment;
    }
}
