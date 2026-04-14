package com.org.transaction.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {

    boolean existsByDocumentNumber(String documentNumber);
}
