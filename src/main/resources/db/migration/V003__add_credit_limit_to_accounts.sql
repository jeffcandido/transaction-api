ALTER TABLE accounts
ADD column available_credit_limit NUMERIC(19, 2) DEFAULT 0
CHECK (available_credit_limit >= 0);