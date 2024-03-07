package com.example.data.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "bank_transaction")
@SQLDelete(sql = "UPDATE bank_transaction SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class BankTransaction {
    @Id
    @GeneratedValue(generator = "bank_transaction_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "bank_transaction_seq",
            sequenceName = "bank_transaction_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public enum Status {
        ACTIVE,
        CANCELLED
    }
}
