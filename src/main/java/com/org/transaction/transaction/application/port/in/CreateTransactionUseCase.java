package com.org.transaction.transaction.application.port.in;

import com.org.transaction.transaction.domain.Transaction;

import java.math.BigDecimal;

public interface CreateTransactionUseCase {

    Transaction createTransaction(Long accountId, Long operationTypeId, BigDecimal amount);
}
