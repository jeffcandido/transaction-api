package com.org.transaction.transaction.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
class TransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "operation_type_id", nullable = false)
    private Long operationTypeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "event_date", nullable = false)
    private Instant eventDate;

    TransactionJpaEntity() {
    }

    TransactionJpaEntity(Long accountId, Long operationTypeId, BigDecimal amount, Instant eventDate) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate;
    }

    Long getId() {
        return id;
    }

    Long getAccountId() {
        return accountId;
    }

    Long getOperationTypeId() {
        return operationTypeId;
    }

    BigDecimal getAmount() {
        return amount;
    }

    Instant getEventDate() {
        return eventDate;
    }
}
