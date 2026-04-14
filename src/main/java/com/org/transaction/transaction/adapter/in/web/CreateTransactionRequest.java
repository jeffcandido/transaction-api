package com.org.transaction.transaction.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Request body for registering a new transaction")
record CreateTransactionRequest(
        @Schema(description = "Cardholder's accountId", example = "1")
        @NotNull
        @JsonProperty("account_id")
        Long accountId,

        @Schema(description = "Operation type ID", example = "2")
        @NotNull
        @JsonProperty("operation_type_id")
        Long operationTypeId,

        @Schema(description = "Transaction amount", example = "100.00")
        @NotNull
        @Positive
        BigDecimal amount
) {
}
