package com.org.transaction.transaction.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.org.transaction.transaction.domain.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

record TransactionResponse(
        @JsonProperty("transaction_id") Long transactionId,
        @JsonProperty("account_id") Long accountId,
        @JsonProperty("operation_type_id") Long operationTypeId,
        BigDecimal amount,
        @JsonProperty("event_date") Instant eventDate
) {
    static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getOperationTypeId(),
                transaction.getAmount(),
                transaction.getEventDate()
        );
    }
}
