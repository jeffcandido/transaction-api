package com.org.transaction.transaction.domain;

public class OperationType {

    private final Long operationTypeId;
    private final String description;

    public OperationType(Long operationTypeId, String description) {
        this.operationTypeId = operationTypeId;
        this.description = description;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public String getDescription() {
        return description;
    }
}
