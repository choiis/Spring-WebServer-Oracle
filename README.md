

# Spring-WebServer-Oracle

* It is a practice project for studying Spring Boot Oracle.

### Tools
* Java Spring Oracle Redis JQuery

### Prepare project environment
* First, prepare Redis, AWS S3

* Execute Oracle DB and Minio S3 with docker

```bash
docker-compose up
```
### Build and Execution

* Build executable jar
```
./mvnw clean package
```

* Run Spring Boot project executable jar
```
./target/Spring-WebServer-Oracle.jar
```

* Check the web project at http://localhost:8090
