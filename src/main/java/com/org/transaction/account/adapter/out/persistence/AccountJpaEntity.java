package com.org.transaction.account.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    AccountJpaEntity() {
    }

    AccountJpaEntity(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    Long getId() {
        return id;
    }

    String getDocumentNumber() {
        return documentNumber;
    }
}
