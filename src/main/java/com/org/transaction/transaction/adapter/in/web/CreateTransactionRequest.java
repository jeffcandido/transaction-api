package com.org.transaction.transaction.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

record CreateTransactionRequest(
        @NotNull
        @JsonProperty("account_id")
        Long accountId,

        @NotNull
        @JsonProperty("operation_type_id")
        Long operationTypeId,

        @NotNull
        @Positive
        BigDecimal amount
) {
}
