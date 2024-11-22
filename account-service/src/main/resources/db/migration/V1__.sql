CREATE TABLE accounts
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    firstname VARCHAR(255)          NOT NULL,
    lastname  VARCHAR(255)          NOT NULL,
    email     VARCHAR(255)          NOT NULL,
    phone     VARCHAR(255)          NOT NULL,
    nas       VARCHAR(255)          NOT NULL,
    password  VARCHAR(255)          NOT NULL,
    balance   DECIMAL               NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT uc_709e98ae55f0bfe26ebfdb494 UNIQUE (nas);