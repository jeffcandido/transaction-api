package com.org.transaction.transaction.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operation_types")
class OperationTypeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_type_id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    OperationTypeJpaEntity() {
    }

    Long getId() {
        return id;
    }

    String getDescription() {
        return description;
    }
}
