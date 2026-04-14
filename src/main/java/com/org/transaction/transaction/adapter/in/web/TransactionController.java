package com.org.transaction.transaction.adapter.in.web;

import com.org.transaction.transaction.application.port.in.CreateTransactionUseCase;
import com.org.transaction.transaction.domain.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    TransactionController(CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TransactionResponse createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        Transaction transaction = createTransactionUseCase.createTransaction(
                request.accountId(),
                request.operationTypeId(),
                request.amount()
        );
        return TransactionResponse.from(transaction);
    }
}
