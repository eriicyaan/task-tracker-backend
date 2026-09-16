--liquibase formatted sql


--changeset create-users:1
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(30) NOT NULL,
    registration_date DATE NOT NULL
);

--changeset create-tasks:2
CREATE TABLE IF NOT EXISTS tasks(
    id UUID PRIMARY KEY,
    header VARCHAR(60) NOT NULL,
    body VARCHAR(256) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    user_id uuid REFERENCES users(id)
);
