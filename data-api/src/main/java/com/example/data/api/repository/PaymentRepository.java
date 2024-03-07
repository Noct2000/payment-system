package com.example.data.api.repository;

import com.example.data.api.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p join fetch p.payer join fetch p.recipient where p.inn = :inn")
    List<Payment> findAllByInn(String inn);

    @Query("select p from Payment p join fetch p.payer join fetch p.recipient where p.okpo = :okpo")
    List<Payment> findAllByOkpo(String okpo);

    @Override
    @Query("select p from Payment p join fetch p.recipient join fetch p.payer")
    List<Payment> findAll();

    @Override
    @Query("select p from Payment p join fetch p.payer join fetch p.recipient where p.id = :id")
    Optional<Payment> findById(Long id);
}
