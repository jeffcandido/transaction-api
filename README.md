# transaction-api

Basic management of the financial lifecycle for cardholders, allowing for account opening and the recording of financial transactions.

## Requirements

- Java 21+
- Maven (or use the included `./mvnw` wrapper)
- Docker (for the local database and container builds)

## Profiles

| Profile | Purpose |
|---------|---------|
| `local` | Local development — connects to the PostgreSQL instance started by `docker-compose` |
| `test`  | Automated tests — used automatically by the test suite via Testcontainers (no manual setup needed) |
| _(none)_ | Production — expects datasource configuration via environment variables (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`) |

## Running locally

### 1. Start the database

```sh
docker compose up -d
```

### 2. Run the application

```sh
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The API will be available at `http://localhost:8080`.

## Running the tests

```sh
./mvnw test
```

Tests spin up an isolated PostgreSQL container automatically via Testcontainers — no manual database setup required.

## API Endpoints

All endpoints are versioned under `/v1`.

### Create account

```sh
curl -s -X POST http://localhost:8080/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{"document_number":"12345678900"}' | jq
```

### Get account

```sh
curl -s http://localhost:8080/v1/accounts/1 | jq
```

### Create transaction

```sh
curl -s -X POST http://localhost:8080/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{"account_id":1,"operation_type_id":4,"amount":123.45}' | jq
```

## Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

## Health check

```sh
curl http://localhost:8080/healthz
```

## Building the Docker image

```sh
docker build -t transaction-api:0.0.1 .
```

## Running the container

```sh
docker run -p 8080:8080 \
  -e DB_HOST=<host> \
  -e DB_PORT=5432 \
  -e DB_NAME=transaction_api \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  transaction-api:0.0.1
```
