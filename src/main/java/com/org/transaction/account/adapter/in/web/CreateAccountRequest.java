package com.org.transaction.account.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for creating a new account")
record CreateAccountRequest(

        @Schema(description = "Cardholder's document number (CPF)", example = "12345678900")
        @JsonProperty("document_number")
        @NotBlank
        String documentNumber
) {
}
