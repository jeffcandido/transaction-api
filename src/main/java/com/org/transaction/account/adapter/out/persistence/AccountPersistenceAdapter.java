package com.org.transaction.account.adapter.out.persistence;

import com.org.transaction.account.application.port.out.AccountRepositoryPort;
import com.org.transaction.account.domain.Account;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class AccountPersistenceAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;

    AccountPersistenceAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        AccountJpaEntity entity = new AccountJpaEntity(account.getDocumentNumber(), account.getAvailableCreditLimit());
        AccountJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Account update(Account account) {
        AccountJpaEntity entity = new AccountJpaEntity(account.getAccountId(), account.getDocumentNumber(), account.getAvailableCreditLimit());
        AccountJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return jpaRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return jpaRepository.findById(accountId).map(this::toDomain);
    }

    private Account toDomain(AccountJpaEntity entity) {
        return new Account(entity.getId(), entity.getDocumentNumber(), entity.getAvailableCreditLimit());
    }
}
