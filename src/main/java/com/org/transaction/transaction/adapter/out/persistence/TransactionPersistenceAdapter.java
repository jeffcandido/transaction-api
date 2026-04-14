package com.org.transaction.transaction.adapter.out.persistence;

import com.org.transaction.transaction.application.port.out.TransactionRepositoryPort;
import com.org.transaction.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
class TransactionPersistenceAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;

    TransactionPersistenceAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionJpaEntity entity = new TransactionJpaEntity(
                transaction.getAccountId(),
                transaction.getOperationTypeId(),
                transaction.getAmount(),
                transaction.getEventDate()
        );
        TransactionJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    private Transaction toDomain(TransactionJpaEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getAccountId(),
                entity.getOperationTypeId(),
                entity.getAmount(),
                entity.getEventDate()
        );
    }
}
