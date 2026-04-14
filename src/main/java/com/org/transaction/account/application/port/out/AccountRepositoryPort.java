package com.org.transaction.account.application.port.out;

import com.org.transaction.account.domain.Account;

import java.util.Optional;

public interface AccountRepositoryPort {

    Account save(Account account);

    boolean existsByDocumentNumber(String documentNumber);

    Optional<Account> findById(Long accountId);
}
