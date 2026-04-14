CREATE TABLE accounts (
    account_id   BIGSERIAL    PRIMARY KEY,
    document_number VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE operation_types (
    operation_type_id BIGSERIAL   PRIMARY KEY,
    description       VARCHAR(50) NOT NULL
);

CREATE TABLE transactions (
    transaction_id    BIGSERIAL      PRIMARY KEY,
    account_id        BIGINT         NOT NULL REFERENCES accounts(account_id),
    operation_type_id BIGINT         NOT NULL REFERENCES operation_types(operation_type_id),
    amount            NUMERIC(19, 2) NOT NULL,
    event_date        TIMESTAMPTZ    NOT NULL
);
