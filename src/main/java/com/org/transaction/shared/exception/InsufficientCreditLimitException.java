package com.org.transaction.shared.exception;

public class InsufficientCreditLimitException extends RuntimeException {

    public InsufficientCreditLimitException(Long accountId) {
        super("Account with ID " + accountId + " has insufficient credit limit for this transaction.");
    }
}
