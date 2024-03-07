package com.example.data.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "payment")
@SQLDelete(sql = "UPDATE payment SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Payment {
    @Id
    @GeneratedValue(generator = "payment_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "payment_id_seq",
            sequenceName = "payment_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false)
    private String inn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id")
    private BankAccount payer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private BankAccount recipient;
    @Column(nullable = false, length = 6)
    private String bic;
    @Column(nullable = false, length = 10)
    private String okpo;
    @Column(name = "mins_before_debiting", nullable = false)
    private Long minsBeforeDebiting;
    private BigDecimal amount = BigDecimal.ZERO;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

}
