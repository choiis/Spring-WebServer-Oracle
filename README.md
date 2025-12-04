
# Spring-WebServer-Oracle

A backend practice project built with **Java 17**, **Spring Boot 3**, **Oracle DB**, and **MyBatis**, including **JWT-based authentication**, **RESTful APIs**, **Spring Batch**, and **MinIO (S3 compatible)** integration.  
The project provides Docker-based local environments for Oracle XE and MinIO.

---

## 1. Project Overview

This repository is a **monolithic backend application** created for studying and practicing:

- Spring Boot 3 project structure and modern Java development
- Oracle DB integration using MyBatis (XML mapper style)
- Stateless JWT Authentication using Spring Security
- RESTful API design for multiple business domains
- SQL-based schema and sample data initialization
- File storage integration using MinIO (S3-compatible)
- Basic batch job examples using Spring Batch

Application default endpoint: http://localhost:8090

---

## 2. Tech Stack

### **Language**
- Java 17

### **Frameworks**
- Spring Boot 3.x
- Spring Web (REST Controller)
- Spring Security + JWT Authentication
- Spring Validation
- Spring AOP
- Spring Batch

### **Database & Storage**
- Oracle XE (via Docker)
- MyBatis (Mapper + XML)
- MinIO (S3-compatible)

### **Build & Tools**
- Gradle
- Docker / Docker Compose
- Swagger / Springdoc OpenAPI
- CircleCI (example CI configuration)

---

## 3. Main Features

### 3.1 **JWT Authentication & Authorization**

The project implements a **token-based login system** with:

- Stateless session handling
- JWT token generation & validation
- Public endpoints vs secure endpoints
- Custom authentication filter

### Authentication Flow

### Login

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

### Protected API Calls

Add the JWT to the HTTP header for all protected API calls, as follows:

```http
Authorization: Bearer {accessToken}
```

## Swagger

- Swagger UI: `http://localhost:8090/swagger-ui/index.html`
- OpenAPI: `http://localhost:8090/v3/api-docs`

### 3.2 **Business Modules (User, Service, Codes, etc.)**

This project includes several sample business modules defined by Oracle SQL scripts inside init/:

Examples include:

* User / Account Management (SM01)
	* CRUD operations
	* Login credentials stored in Oracle
	* MyBatis-based mappings
* Various Service / Code / Reference Tables
	* Multiple entities for testing business flows
	* Demonstrates mapper XML, DTOs, services, controller design
* Batch Jobs
	* Example Spring Batch configurations
	* Demonstrates Reader → Processor → Writer flow
* AOP Logging
	* Method tracing
	* Performance measurement
	* Request/response logging

### 3.3 **MinIO (S3-compatible) File Storage Integration**

The project supports local S3-style storage using MinIO:

* File upload / download example API
* Configurable bucket name, access key, endpoint URL
* Same pattern used for AWS S3 → easily extendable

## 4. Prerequisites

Install the following:

* JDK 17
* Docker & Docker Compose
* Gradle
* 4GB+ RAM recommended for Oracle XE Docker container

Also ensure:
* Ports (1521 for Oracle, 9000 for MinIO) are not occupied.
* Your machine has enough memory for Oracle to start properly.

## 5. Running the Project

### 5.1 **Start Oracle & MinIO via Docker Compose**

From project root:
```bash
docker-compose up
```
This will start Oracle XE database and MinIO server

### 5.2 **Start the Spring Application**

Using Gradle wrapper:
```bash
./gradlew clean bootRun
```

Or build JAR:
```bash
./gradlew clean build
java -jar build/libs/*.jar
```

App starts at: http://localhost:8090

## 6. API Documentation (Swagger / OpenAPI)

Swagger UI available at:
```
http://localhost:8090/swagger-ui/index.html
```

OpenAPI JSON:
```
http://localhost:8090/v3/api-docs
```

Testing Secured Endpoints

1. Login via /auth/login

2. Copy token

3. Click Authorize in Swagger UI

4. Enter:
```
Bearer {accessToken}
```