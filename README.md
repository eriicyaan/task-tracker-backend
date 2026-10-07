# Task Tracker Backend

Backend многопользовательского планировщика задач.

Пользователи могут регистрироваться, авторизовываться и управлять своими задачами.

## Возможности

* Регистрация и авторизация пользователей
* JWT-аутентификация
* Выход из аккаунта
* Создание задач
* Редактирование задач
* Удаление задач
* Изменение статуса задачи
* Получение списка задач пользователя
* Отправка событий в Kafka

## Технологии

**Backend:**

* Java
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* Hibernate
* JWT
* Maven

**База данных:**

* PostgreSQL
* Liquibase

**Messaging:**

* Apache Kafka
* Spring Kafka

**DevOps:**

* Docker
* Docker Compose

## API Endpoints

### Authentication

| Method | Endpoint             | Description              |
| ------ |----------------------| ------------------------ |
| POST   | `/api/auth/sign-up`  | Регистрация пользователя |
| POST   | `/api/auth/sign-in`  | Авторизация пользователя |
| POST   | `/api/auth/sign-out` | Выход из аккаунта        |

### User

| Method | Endpoint    | Description                     |
| ------ | ----------- | ------------------------------- |
| GET    | `/api/user` | Получение текущего пользователя |

### Tasks

| Method | Endpoint          | Description                           |
| ------ | ----------------- | ------------------------------------- |
| GET    | `/api/tasks`      | Получение задач текущего пользователя |
| POST   | `/api/tasks`      | Создание задачи                       |
| PATCH  | `/api/tasks/{id}` | Редактирование задачи                 |
| DELETE | `/api/tasks/{id}` | Удаление задачи                       |

## Клонирование репозитория

```bash

git clone https://github.com/eriicyaan/task-tracker-backend.git
```

## Запуск

Для запуска всего проекта используйте инфраструктурный репозиторий:

[task-tracker-infrastructure](https://github.com/eriicyaan/task-tracker-infrastructure)
