package com.org.transaction.account.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.org.transaction.account.domain.Account;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Account details")
record AccountResponse(

        @Schema(description = "Unique account identifier", example = "1")
        @JsonProperty("account_id")
        Long accountId,

        @Schema(description = "Cardholder's document number (CPF)", example = "12345678900")
        @JsonProperty("document_number")
        String documentNumber,

        @Schema(description = "Cardholder's credit limit", example = "10.0")
        @JsonProperty("available_credit_limit")
        BigDecimal availableCreditLimit
) {

    static AccountResponse from(Account account) {
        return new AccountResponse(account.getAccountId(), account.getDocumentNumber(), account.getAvailableCreditLimit());
    }
}
