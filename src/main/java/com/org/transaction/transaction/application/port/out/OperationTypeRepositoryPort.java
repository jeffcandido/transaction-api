package com.org.transaction.transaction.application.port.out;

import com.org.transaction.transaction.domain.OperationType;

import java.util.Optional;

public interface OperationTypeRepositoryPort {

    Optional<OperationType> findById(Long operationTypeId);
}
