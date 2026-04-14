package com.org.transaction.transaction.adapter.out.persistence;

import com.org.transaction.transaction.application.port.out.OperationTypeRepositoryPort;
import com.org.transaction.transaction.domain.OperationType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class OperationTypePersistenceAdapter implements OperationTypeRepositoryPort {

    private final OperationTypeJpaRepository jpaRepository;

    OperationTypePersistenceAdapter(OperationTypeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<OperationType> findById(Long operationTypeId) {
        return jpaRepository.findById(operationTypeId).map(this::toDomain);
    }

    private OperationType toDomain(OperationTypeJpaEntity entity) {
        return new OperationType(entity.getId(), entity.getDescription());
    }
}
