package com.example.data.api.repository;

import com.example.data.api.model.BankTransaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    @Query("""
            select bt
            from BankTransaction bt
            join fetch bt.payment p
            join fetch p.payer
            join fetch p.recipient
            where bt.payment.id = :paymentId
            """)
    List<BankTransaction> findAllByPaymentId(Long paymentId);

    @Override
    @Query("""
            select bt
            from BankTransaction bt
            join fetch bt.payment p
            join fetch p.payer
            join fetch p.recipient
            where bt.id = :id
            """)
    Optional<BankTransaction> findById(Long id);

    @Override
    @Query("""
            select bt
            from BankTransaction bt
            join fetch bt.payment p
            join fetch p.payer
            join fetch p.recipient
            """)
    List<BankTransaction> findAll();
}
