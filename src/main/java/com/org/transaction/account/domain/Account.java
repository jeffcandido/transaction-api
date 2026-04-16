package com.org.transaction.account.domain;

import java.math.BigDecimal;

public class Account {

    private final Long accountId;
    private final String documentNumber;
    private final BigDecimal availableCreditLimit;

    public Account(Long accountId, String documentNumber, BigDecimal availableCreditLimit) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.availableCreditLimit = availableCreditLimit;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public BigDecimal getAvailableCreditLimit() {
        return availableCreditLimit;
    }
}
