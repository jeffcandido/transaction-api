package com.org.transaction.account.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @Column(name = "available_credit_limit", nullable = false)
    private BigDecimal availableCreditLimit;

    protected AccountJpaEntity() {
    }

    AccountJpaEntity(String documentNumber, BigDecimal availableCreditLimit) {
        this.documentNumber = documentNumber;
        this.availableCreditLimit = availableCreditLimit;
    }

    AccountJpaEntity(Long id, String documentNumber, BigDecimal availableCreditLimit) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.availableCreditLimit = availableCreditLimit;
    }

    Long getId() {
        return id;
    }

    String getDocumentNumber() {
        return documentNumber;
    }

    BigDecimal getAvailableCreditLimit() {
        return availableCreditLimit;
    }
}
