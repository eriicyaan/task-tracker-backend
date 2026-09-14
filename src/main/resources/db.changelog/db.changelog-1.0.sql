--liquibase formatted sql


--changeset create-users:1
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    registration_date DATE NOT NULL
);

--changeset create-tasks:2
CREATE TABLE IF NOT EXISTS tasks(
    id BIGSERIAL PRIMARY KEY,
    header VARCHAR(60) NOT NULL,
    body VARCHAR(256) NOT NULL,
    status VARCHAR(30) NOT NULL,
    done_at DATE NOT NULL,
    user_id BIGINT REFERENCES users(id)
);

