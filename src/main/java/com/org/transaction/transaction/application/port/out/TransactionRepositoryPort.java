package com.org.transaction.transaction.application.port.out;

import com.org.transaction.transaction.domain.Transaction;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);
}
