package com.org.transaction.transaction.application.service;

import com.org.transaction.account.application.port.out.AccountRepositoryPort;
import com.org.transaction.account.domain.Account;
import com.org.transaction.shared.exception.AccountNotFoundException;
import com.org.transaction.shared.exception.InsufficientCreditLimitException;
import com.org.transaction.shared.exception.OperationTypeNotFoundException;
import com.org.transaction.transaction.application.port.out.OperationTypeRepositoryPort;
import com.org.transaction.transaction.application.port.out.TransactionRepositoryPort;
import com.org.transaction.transaction.domain.OperationType;
import com.org.transaction.transaction.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountRepositoryPort accountRepository;

    @Mock
    private OperationTypeRepositoryPort operationTypeRepository;

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldCreateDebitTransactionWithNegativeAmount() {
        Long accountId = 1L;
        Long operationTypeId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, "12345678900", new BigDecimal("500.00"))));
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(new OperationType(operationTypeId, "COMPRA A VISTA")));
        when(transactionRepository.save(any())).thenAnswer(inv -> {
            Transaction t = inv.getArgument(0);
            return new Transaction(1L, t.getAccountId(), t.getOperationTypeId(), t.getAmount(), t.getEventDate());
        });

        Transaction result = transactionService.createTransaction(accountId, operationTypeId, amount);

        assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("-100.00"));
    }

    @Test
    void shouldCreatePaymentTransactionWithPositiveAmount() {
        Long accountId = 1L;
        Long operationTypeId = 4L;
        BigDecimal amount = new BigDecimal("200.00");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, "12345678900", BigDecimal.ZERO)));
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(new OperationType(operationTypeId, "PAGAMENTO")));
        when(transactionRepository.save(any())).thenAnswer(inv -> {
            Transaction t = inv.getArgument(0);
            return new Transaction(2L, t.getAccountId(), t.getOperationTypeId(), t.getAmount(), t.getEventDate());
        });

        Transaction result = transactionService.createTransaction(accountId, operationTypeId, amount);

        assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("200.00"));
    }

    @Test
    void shouldThrowInsufficientCreditLimitExceptionWhenDebitExceedsLimit() {
        Long accountId = 1L;
        Long operationTypeId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, "12345678900", new BigDecimal("50.00"))));
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(new OperationType(operationTypeId, "COMPRA A VISTA")));

        assertThatThrownBy(() -> transactionService.createTransaction(accountId, operationTypeId, amount))
                .isInstanceOf(InsufficientCreditLimitException.class)
                .hasMessageContaining(String.valueOf(accountId));
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() {
        Long accountId = 99L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.createTransaction(accountId, 1L, new BigDecimal("50.00")))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining(String.valueOf(accountId));
    }

    @Test
    void shouldThrowOperationTypeNotFoundExceptionWhenOperationTypeDoesNotExist() {
        Long accountId = 1L;
        Long operationTypeId = 99L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, "12345678900", BigDecimal.ZERO)));
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.createTransaction(accountId, operationTypeId, new BigDecimal("50.00")))
                .isInstanceOf(OperationTypeNotFoundException.class)
                .hasMessageContaining(String.valueOf(operationTypeId));
    }

    @Test
    void shouldNegateAlreadyNegativeDebitAmountInput() {
        Long accountId = 1L;
        Long operationTypeId = 3L;
        BigDecimal amount = new BigDecimal("75.50");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, "12345678900", new BigDecimal("500.00"))));
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(new OperationType(operationTypeId, "SAQUE")));
        when(transactionRepository.save(any())).thenAnswer(inv -> {
            Transaction t = inv.getArgument(0);
            return new Transaction(3L, t.getAccountId(), t.getOperationTypeId(), t.getAmount(), t.getEventDate());
        });

        Transaction result = transactionService.createTransaction(accountId, operationTypeId, amount);

        assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("-75.50"));
        assertThat(result.getEventDate()).isNotNull();
    }
}
