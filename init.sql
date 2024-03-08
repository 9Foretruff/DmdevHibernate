CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    username   VARCHAR(128) UNIQUE,
    role       VARCHAR(32),
    info       JSONB,
    company_id INT REFERENCES company (id)
--     PRIMARY KEY (firstname, lastname, birth_date)
);

CREATE TABLE users_chat
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE,
    chat_id    BIGINT REFERENCES chat (id) ON DELETE CASCADE,
    created_at TIMESTAMP    NOT NULL,
    created_by VARCHAR(128) NOT NULL
);

DROP TABLE users_chat;

INSERT INTO users_chat(user_id, chat_id)
VALUES (5, 1);

DELETE
FROM users
WHERE id = 5;

CREATE TABLE chat
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE company
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE profile
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT UNIQUE NOT NULL REFERENCES users (id),
    street   VARCHAR(128),
    language CHAR(2)
);

DROP TABLE profile;


CREATE SEQUENCE users_id_seq
    OWNED BY users.id;

DROP TABLE users;