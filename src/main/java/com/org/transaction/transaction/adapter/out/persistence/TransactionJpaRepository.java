package com.org.transaction.transaction.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {
}
