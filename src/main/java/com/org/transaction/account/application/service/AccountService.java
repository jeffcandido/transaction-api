package com.org.transaction.account.application.service;

import com.org.transaction.account.application.port.in.CreateAccountUseCase;
import com.org.transaction.account.application.port.out.AccountRepositoryPort;
import com.org.transaction.account.domain.Account;
import com.org.transaction.shared.exception.DuplicateDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService implements CreateAccountUseCase {

    private final AccountRepositoryPort accountRepository;

    public AccountService(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String documentNumber) {
        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            throw new DuplicateDocumentException(documentNumber);
        }
        return accountRepository.save(new Account(null, documentNumber));
    }
}
