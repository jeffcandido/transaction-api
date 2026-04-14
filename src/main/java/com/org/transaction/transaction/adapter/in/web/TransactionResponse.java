package com.org.transaction.transaction.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.org.transaction.transaction.domain.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Transaction details")
record TransactionResponse(
        @Schema(description = "Unique transaction identifier", example = "1")
        @JsonProperty("transaction_id") Long transactionId,
        @Schema(description = "Cardholder's accountId", example = "1")
        @JsonProperty("account_id") Long accountId,
        @Schema(description = "Operation type ID", example = "2")
        @JsonProperty("operation_type_id") Long operationTypeId,
        @Schema(description = "Transaction amount", example = "100.00")
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
