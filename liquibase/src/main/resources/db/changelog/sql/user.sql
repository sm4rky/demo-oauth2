CREATE TABLE user (
    id           VARCHAR(36)  NOT NULL PRIMARY KEY,
    first_name   VARCHAR(64)  NOT NULL,
    last_name    VARCHAR(64)  NOT NULL,
    email        VARCHAR(64)  NOT NULL UNIQUE,
    password     VARCHAR(64)  NOT NULL,
    is_verified  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP    DEFAULT NULL,
    modified_at  TIMESTAMP    DEFAULT NULL,
    created_by   VARCHAR(36)  DEFAULT NULL,
    modified_by  VARCHAR(36)  DEFAULT NULL
);
