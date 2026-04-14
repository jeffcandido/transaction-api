package com.org.transaction.shared.exception;

public class OperationTypeNotFoundException extends RuntimeException {

    public OperationTypeNotFoundException(Long operationTypeId) {
        super("Operation type with ID " + operationTypeId + " not found.");
    }
}
