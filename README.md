# Task Manager API with Spring Boot

A compact in-memory task API designed to demonstrate endpoint-level tests with MockMvc: success responses, validation, not-found behaviour, and JSON contracts.

## Run
```bash
mvn test
mvn spring-boot:run
```

Endpoints: `POST /api/tasks`, `GET /api/tasks/{id}`, `GET /api/tasks`.

## Test strategy
- HTTP status and JSON response contract assertions
- Bean validation and malformed/invalid request handling
- Not-found response behaviour
- Isolated in-memory store (no external database required)

Portfolio project; review and extend it yourself before publishing.
