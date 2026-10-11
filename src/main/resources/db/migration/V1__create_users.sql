CREATE TABLE users (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    email               VARCHAR(150) NOT NULL UNIQUE,
    password_hash       VARCHAR(255) NOT NULL,
    phone               VARCHAR(20),
    address             VARCHAR(255),
    driving_license_no  VARCHAR(30),
    role                VARCHAR(20)  NOT NULL DEFAULT 'CUSTOMER',
    active              BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at          DATETIME     NOT NULL
);
