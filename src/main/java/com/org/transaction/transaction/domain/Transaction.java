package com.org.transaction.transaction.domain;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction {

    private final Long transactionId;
    private final Long accountId;
    private final Long operationTypeId;
    private final BigDecimal amount;
    private final Instant eventDate;

    public Transaction(Long transactionId, Long accountId, Long operationTypeId,
                       BigDecimal amount, Instant eventDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
        this.eventDate = eventDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getEventDate() {
        return eventDate;
    }
}
