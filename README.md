

# Spring-WebServer-Oracle

* It is a practice project for studying Spring Boot and Oracle DB.

### Tools
* Java Spring Oracle

### Prepare project environment
* Execute Oracle DB and Minio S3 with docker

```bash
docker-compose up
```

### Execution

```bash
./gradlew bootRun
```

Default port: `http://localhost:8090`

### Authentication Flow

### 1) Login

- POST `/api/v1/auth/login`
- Request Body:

```json
{
"username": "admin",
"password": "admin1234"
}
```

Or the `userid`/`passwd` combination from the `SM01` table in the DB (in production)

- Response:

```json
{
"accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 2) Protected API Calls

Add the JWT to the HTTP header for all protected API calls, as follows:

```http
Authorization: Bearer {accessToken}
```

## Swagger

- Swagger UI: `http://localhost:8090/swagger-ui/index.html`
- OpenAPI: `http://localhost:8090/v3/api-docs`

