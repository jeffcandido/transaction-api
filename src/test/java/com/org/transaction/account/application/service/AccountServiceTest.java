package com.org.transaction.account.application.service;

import com.org.transaction.account.application.port.out.AccountRepositoryPort;
import com.org.transaction.account.domain.Account;
import com.org.transaction.shared.exception.AccountNotFoundException;
import com.org.transaction.shared.exception.DuplicateDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepositoryPort accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        String documentNumber = "12345678900";
        when(accountRepository.existsByDocumentNumber(documentNumber)).thenReturn(false);
        when(accountRepository.save(any())).thenReturn(new Account(1L, documentNumber, BigDecimal.ZERO));

        Account result = accountService.createAccount(documentNumber);

        assertThat(result.getAccountId()).isEqualTo(1L);
        assertThat(result.getDocumentNumber()).isEqualTo(documentNumber);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldThrowDuplicateDocumentExceptionWhenDocumentAlreadyExists() {
        String documentNumber = "12345678900";
        when(accountRepository.existsByDocumentNumber(documentNumber)).thenReturn(true);

        assertThatThrownBy(() -> accountService.createAccount(documentNumber))
                .isInstanceOf(DuplicateDocumentException.class)
                .hasMessageContaining(documentNumber);

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldFindAccountSuccessfully() {
        Long accountId = 1L;
        Account account = new Account(accountId, "12345678900", BigDecimal.ZERO);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account result = accountService.findAccount(accountId);

        assertThat(result.getAccountId()).isEqualTo(accountId);
        assertThat(result.getDocumentNumber()).isEqualTo("12345678900");
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() {
        Long accountId = 99L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findAccount(accountId))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining(String.valueOf(accountId));
    }
}
