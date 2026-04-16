package com.org.transaction.transaction.application.service;

import com.org.transaction.account.application.port.out.AccountRepositoryPort;
import com.org.transaction.account.domain.Account;
import com.org.transaction.shared.exception.AccountNotFoundException;
import com.org.transaction.shared.exception.InsufficientCreditLimitException;
import com.org.transaction.shared.exception.OperationTypeNotFoundException;
import com.org.transaction.transaction.application.port.in.CreateTransactionUseCase;
import com.org.transaction.transaction.application.port.out.OperationTypeRepositoryPort;
import com.org.transaction.transaction.application.port.out.TransactionRepositoryPort;
import com.org.transaction.transaction.domain.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Service
@Transactional
public class TransactionService implements CreateTransactionUseCase {

    private static final Set<Long> DEBIT_OPERATION_TYPES = Set.of(1L, 2L, 3L);

    private final AccountRepositoryPort accountRepository;
    private final OperationTypeRepositoryPort operationTypeRepository;
    private final TransactionRepositoryPort transactionRepository;

    public TransactionService(AccountRepositoryPort accountRepository,
                              OperationTypeRepositoryPort operationTypeRepository,
                              TransactionRepositoryPort transactionRepository) {
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(Long accountId, Long operationTypeId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        operationTypeRepository.findById(operationTypeId)
                .orElseThrow(() -> new OperationTypeNotFoundException(operationTypeId));

        BigDecimal signedAmount = DEBIT_OPERATION_TYPES.contains(operationTypeId)
                ? amount.abs().negate()
                : amount.abs();

        BigDecimal newAvailableCreditLimit = account.getAvailableCreditLimit().add(signedAmount);

        if (newAvailableCreditLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientCreditLimitException(accountId);
        }

        accountRepository.update(new Account(account.getAccountId(), account.getDocumentNumber(), newAvailableCreditLimit));

        Transaction transaction = new Transaction(null, accountId, operationTypeId, signedAmount, Instant.now());
        return transactionRepository.save(transaction);
    }
}
