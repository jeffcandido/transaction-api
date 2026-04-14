# transaction-api

Basic management of the financial lifecycle for cardholders, allowing for account opening and the recording of financial transactions.

## Build the image

```sh
docker build -t transaction-api:0.0.1 .
```

## Run the container

```sh
docker run -p 8080:8080 transaction-api:0.0.1
```

## Check application health

```sh
curl http://localhost:8080/healthz
```

## Check application Swagger

Go to <http://localhost:8080/swagger-ui/index.html>
