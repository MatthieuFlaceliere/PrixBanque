CREATE TABLE tokens
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    token      VARCHAR(255) NOT NULL,
    account_id BIGINT NULL,
    CONSTRAINT pk_tokens PRIMARY KEY (id)
);

ALTER TABLE tokens
    ADD CONSTRAINT FK_TOKENS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);