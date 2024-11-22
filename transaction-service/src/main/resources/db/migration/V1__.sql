CREATE TABLE transactions
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    sender_account   VARCHAR(255) NOT NULL,
    receiver_account VARCHAR(255) NOT NULL,
    amount              DECIMAL      NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);