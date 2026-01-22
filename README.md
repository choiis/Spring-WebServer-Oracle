
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
- Spring Boot 3.3.4
- Spring Web (REST Controller)
- Spring Security + JWT Authentication (jjwt 0.11.5)
- Spring Validation
- Spring AOP
- Spring Quartz (Scheduling)

### **Database & Storage**
- Oracle XE 11g (via Docker)
- MyBatis 3.0.3 (Mapper + XML)
- MinIO (S3-compatible, RELEASE.2025-07-23)
- AWS SDK v2 for S3 integration

### **Build & Tools**
- Gradle 8.x
- Docker / Docker Compose 3.3
- Swagger / Springdoc OpenAPI 2.6.0
- Lombok 1.18.34
- CircleCI (example CI configuration)

---

## 3. Project Structure

```
Spring-WebServer-Oracle/
├── .circleci/                    # CircleCI CI/CD configuration
├── init/
│   └── table.sql                 # Oracle DB schema & sample data
├── src/
│   └── main/
│       ├── java/com/singer/
│       │   ├── SpringWebServerOracleApplication.java
│       │   ├── application/
│       │   │   ├── controller/   # REST API Controllers
│       │   │   │   ├── comm/     # Auth, Common, Stream
│       │   │   │   ├── sb/       # Video Board (SB01, SB02)
│       │   │   │   ├── sf/       # File Board (SF01, SF02)
│       │   │   │   ├── sm/       # User/Memo (SM01, SM02, SMI1)
│       │   │   │   ├── sr/       # Restaurant Board (SR01-SR03)
│       │   │   │   └── sv/       # Vote Board (SV01, SV02, SV04)
│       │   │   ├── dto/          # Request/Response DTOs
│       │   │   └── service/      # Business Logic Services
│       │   ├── common/
│       │   │   ├── bean/         # Bean Configurations
│       │   │   ├── exception/    # Custom Exceptions & Handlers
│       │   │   └── util/         # Utility Classes
│       │   ├── domain/
│       │   │   ├── dao/          # MyBatis DAO Interfaces
│       │   │   └── entity/       # Domain Entities
│       │   └── infrastructure/
│       │       ├── aop/          # AOP Logging Advice
│       │       ├── batch/        # Spring Batch Jobs
│       │       ├── config/       # OpenAPI, S3 Config
│       │       ├── security/     # JWT & Security Config
│       │       └── util/         # S3 Utility
│       └── resources/
│           ├── application.yml   # Application Configuration
│           └── mappers/          # MyBatis XML Mappers
├── build.gradle                  # Gradle Build Configuration
├── docker-compose.yml            # Docker Services (Oracle, MinIO)
└── README.md
```

---

## 4. Main Features

### 4.1 **JWT Authentication & Authorization**

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

### 4.2 **Business Modules**

This project includes several sample business modules:

| Module | Table | Description |
|--------|-------|-------------|
| **User Management** | SM01, SMP1, SMI1, SME1 | User accounts, profile photos, user info, user events |
| **Video Board** | SB01, SB02, SBV1, SBG1 | Video posts, comments, video paths, like/hate logs |
| **File Board** | SF01, SF02, SFG1, SFD1 | File posts, comments, like logs, download logs |
| **Vote Board** | SV01, SV02, SV03, SV04, SVG1 | Vote posts, options, votes, comments, like logs |
| **Restaurant Board** | SR01, SR02, SR03, SRP1, SRG1 | Restaurant posts, ratings, comments, photos, like logs |
| **Memo** | SM02 | Personal memo/notes |
| **Code Management** | CODE_GRP, CODE, MENU | System codes, menus |

### 4.3 **MinIO (S3-compatible) File Storage Integration**

The project supports local S3-style storage using MinIO:

* File upload / download example API
* Configurable bucket name, access key, endpoint URL
* Same pattern used for AWS S3 → easily extendable

### 4.4 **AOP Logging**

* Method tracing and performance measurement
* Request/response logging
* Implemented in `AopAdvice.java`

---

## 5. API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/login` | Login and get JWT token |

### User Management (SM)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/sm01/{nowPage}` | Get user list |
| GET | `/api/v1/sm01/one/{userid}` | Get user detail |
| POST | `/api/v1/sm01` | Create user |
| PUT | `/api/v1/sm01/{userid}` | Update user |
| DELETE | `/api/v1/sm01/{userid}` | Delete user |

### Video Board (SB)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/sb01/{nowPage}` | Get video post list |
| GET | `/api/v1/sb01/one/{seq}` | Get video post detail |
| POST | `/api/v1/sb01` | Create video post |
| DELETE | `/api/v1/sb01/{seq}` | Delete video post |
| PATCH | `/api/v1/sb01/like/{seq}` | Like video post |
| GET | `/api/v1/sb02/{seq01}/{nowPage}` | Get comments |
| POST | `/api/v1/sb02` | Create comment |

### File Board (SF)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/sf01/{nowPage}` | Get file post list |
| POST | `/api/v1/sf01` | Create file post (with upload) |
| DELETE | `/api/v1/sf01/{seq}` | Delete file post |
| GET | `/api/v1/sf02/{seq01}/{nowPage}` | Get comments |

### Vote Board (SV)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/sv01/{nowPage}` | Get vote list |
| GET | `/api/v1/sv01/one/{seq}/{recall}` | Get vote detail |
| POST | `/api/v1/sv01` | Create vote |
| PUT | `/api/v1/sv01/sv01/{seq}` | Update vote |
| DELETE | `/api/v1/sv01/{seq}` | Delete vote |
| PATCH | `/api/v1/sv01/like/{seq}` | Like vote |
| POST | `/api/v1/sv02` | Submit vote option |

### Restaurant Board (SR)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/sr01/{nowPage}` | Get restaurant list |
| GET | `/api/v1/sr01/one/{seq}` | Get restaurant detail |
| POST | `/api/v1/sr01` | Create restaurant post |
| DELETE | `/api/v1/sr01/{seq}` | Delete restaurant post |
| POST | `/api/v1/sr02` | Rate restaurant |
| GET | `/api/v1/sr03/{seq01}/{nowPage}` | Get comments |

### Common
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/comm/code/{codegrp}` | Get codes by group |
| GET | `/api/v1/comm/menu` | Get menu list |

---

## 6. Database Schema

### Entity Relationship

```
SM01 (User) ──┬── SMP1 (Profile Photo)
              ├── SMI1 (User Info)
              ├── SME1 (User Event)
              ├── SM02 (Memo)
              ├── SB01 (Video Post) ──┬── SB02 (Comment)
              │                       ├── SBV1 (Video Path)
              │                       └── SBG1 (Like Log)
              ├── SF01 (File Post) ──┬── SF02 (Comment)
              │                      ├── SFG1 (Like Log)
              │                      └── SFD1 (Download Log)
              ├── SV01 (Vote Post) ──┬── SV02 (Option) ── SV03 (Vote)
              │                      ├── SV04 (Comment)
              │                      └── SVG1 (Like Log)
              └── SR01 (Restaurant) ──┬── SR02 (Rating)
                                      ├── SR03 (Comment)
                                      ├── SRP1 (Photo)
                                      └── SRG1 (Like Log)
```

### Key Tables

| Table | Description | Primary Key |
|-------|-------------|-------------|
| SM01 | User accounts | userid |
| SB01 | Video board posts | seq (auto-increment) |
| SF01 | File board posts | seq (auto-increment) |
| SV01 | Vote board posts | seq (auto-increment) |
| SR01 | Restaurant board posts | seq (auto-increment) |
| CODE_GRP | Code group master | codegrp |
| CODE | Code detail | codegrp + codecd |
| MENU | System menu | menucd |

---

## 7. Prerequisites

Install the following:

* JDK 17+
* Docker & Docker Compose
* Gradle 8.x (or use included wrapper)
* 4GB+ RAM recommended for Oracle XE Docker container

Also ensure:
* Port 11521 (Oracle), 9000 (MinIO API), 9001 (MinIO Console) are not occupied
* Your machine has enough memory for Oracle to start properly

---

## 8. Running the Project

### 8.1 **Start Oracle & MinIO via Docker Compose**

From project root:
```bash
docker-compose up -d
```

This starts:
- **Oracle XE 11g** on port `11521` (internal 1521)
- **MinIO Server** on port `9000` (API) and `9001` (Console)
- **Auto bucket creation** (`s3-storage` bucket)

Wait for Oracle to fully initialize (check logs with `docker-compose logs -f oracle`).

### 8.2 **Start the Spring Application**

Using Gradle wrapper:
```bash
./gradlew clean bootRun
```

Or build JAR:
```bash
./gradlew clean build
java -jar build/libs/Spring-WebServer-Oracle-0.0.1-SNAPSHOT.jar
```

App starts at: http://localhost:8090

---

## 9. Configuration

### application.yml Key Settings

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 8090 | Application port |
| `spring.datasource.url` | jdbc:oracle:thin:@127.0.0.1:11521/xe | Oracle connection URL |
| `spring.datasource.username` | insung | DB username |
| `spring.datasource.password` | 12345678 | DB password |
| `aws.s3.endpoint` | http://localhost:9000 | MinIO endpoint |
| `aws.s3.bucketName` | s3-storage | S3 bucket name |
| `aws.s3.access` | minioadmin | MinIO access key |
| `aws.s3.secret` | minioadmin | MinIO secret key |
| `jwt.secret` | (base64 encoded) | JWT signing key |
| `jwt.expiration-seconds` | 3600 | Token expiration (1 hour) |

### Docker Services

| Service | Image | Ports | Purpose |
|---------|-------|-------|---------|
| oracle | oracleinanutshell/oracle-xe-11g | 11521:1521 | Oracle XE Database |
| minio | minio/minio:RELEASE.2025-07-23 | 9000, 9001 | S3-compatible storage |
| createbuckets | minio/mc | - | Auto bucket creation |

---

## 10. API Documentation (Swagger / OpenAPI)

Swagger UI available at:
```
http://localhost:8090/swagger-ui/index.html
```

OpenAPI JSON:
```
http://localhost:8090/v3/api-docs
```

### Testing Secured Endpoints

1. Login via `POST /api/v1/auth/login`
2. Copy the `accessToken` from response
3. Click **Authorize** in Swagger UI
4. Enter: `Bearer {accessToken}`

### Test Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | admin1234 | Administrator |
| user01 | pass01 | Normal User |

---

## 11. MinIO Console

Access MinIO web console:
```
http://localhost:9001
```

Login credentials:
- **Username**: minioadmin
- **Password**: minioadmin

---

## 12. Troubleshooting

### Oracle Container Issues

```bash
# Check Oracle logs
docker-compose logs oracle

# Restart Oracle container
docker-compose restart oracle

# Reset Oracle data (caution: deletes all data)
docker-compose down -v
docker-compose up -d
```

### Connection Refused

- Ensure Docker containers are running: `docker-compose ps`
- Wait for Oracle initialization (can take 2-3 minutes on first start)
- Check if ports are available: `netstat -an | grep 11521`

### JWT Token Expired

- Default expiration is 1 hour
- Re-login to get a new token

---

## 13. Development Notes

### Adding New API Module

1. Create Entity in `domain/entity/{module}/`
2. Create DAO interface in `domain/dao/{module}/`
3. Create MyBatis mapper XML in `resources/mappers/`
4. Create DTOs in `application/dto/{module}/`
5. Create Service in `application/service/{module}/`
6. Create Controller in `application/controller/{module}/`

### Code Conventions

- Entity naming: `{MODULE}{SEQ}Entity.java` (e.g., `SB01Entity.java`)
- Mapper naming: `{MODULE}{SEQ}VoMapper.xml` (e.g., `SB01VoMapper.xml`)
- API prefix: `/api/v1/{module}`

---

## 14. License

This project is for educational and practice purposes.