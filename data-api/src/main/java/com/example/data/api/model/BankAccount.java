package com.example.data.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "bank_account")
@SQLDelete(sql = "UPDATE bank_account SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class BankAccount {
    @Id
    @GeneratedValue(generator = "bank_account_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "bank_account_id_seq",
            sequenceName = "bank_account_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false, length = 30)
    private String iban;
    @Column(name = "account_number", unique = true, nullable = false, length = 16)
    private String accountNumber;
    private BigDecimal amount = BigDecimal.ZERO;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

}
