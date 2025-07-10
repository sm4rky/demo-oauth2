CREATE TABLE verification_token(
    token           VARCHAR(36)         PRIMARY KEY,
    type            VARCHAR(15)         NOT NULL,
    user_id         VARCHAR(36)         NOT NULL,
    expires_at      TIMESTAMP           NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE
)