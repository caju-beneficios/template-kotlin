CREATE TABLE person
(
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    version     INT          NOT NULL,
    name        VARCHAR(255) NULL,
    email       VARCHAR(255) NULL,
    cpf         VARCHAR(255),
    created_at  DATETIME     NOT NULL DEFAULT NOW(),
    modified_at DATETIME     NULL     DEFAULT NOW(),
    CONSTRAINT uc_person_cpf UNIQUE (cpf)
);
