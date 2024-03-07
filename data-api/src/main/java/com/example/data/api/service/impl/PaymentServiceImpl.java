package com.example.data.api.service.impl;

import com.example.data.api.model.Payment;
import com.example.data.api.repository.PaymentRepository;
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
        paymentRepository.save(entity);
        return entity;
    }

    @Override
    public List<Payment> findAllByInn(String inn) {
        return paymentRepository.findAllByInn(inn);
    }

    @Override
    public List<Payment> findAllByOkpo(String okpo) {
        return paymentRepository.findAllByOkpo(okpo);
    }
}
