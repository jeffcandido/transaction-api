package com.org.transaction.account.application.port.in;

import com.org.transaction.account.domain.Account;

public interface FindAccountUseCase {

    Account findAccount(Long accountId);
}
