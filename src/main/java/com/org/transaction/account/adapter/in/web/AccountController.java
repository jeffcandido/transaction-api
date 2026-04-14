package com.org.transaction.account.adapter.in.web;

import com.org.transaction.account.application.port.in.CreateAccountUseCase;
import com.org.transaction.account.domain.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Accounts", description = "Account management")
class AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    AccountController(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new account",
            description = "Registers a new cardholder account. The document number must be unique.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully",
                            content = @Content(schema = @Schema(implementation = AccountResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "409", description = "Account with this document number already exists")
            }
    )
    AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = createAccountUseCase.createAccount(request.documentNumber());
        return AccountResponse.from(account);
    }
}
