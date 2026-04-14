package com.org.transaction.shared.exception;

public class DuplicateDocumentException extends RuntimeException {

    public DuplicateDocumentException(String documentNumber) {
        super("Account with document number '" + documentNumber + "' already exists.");
    }
}
