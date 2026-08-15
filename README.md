# Job Board REST API — Spring Boot Backend

Personal R&D project — built to deepen full‑stack architecture understanding, so I can design and build **more robust mobile ↔ server integrations as a Senior Android Developer**.

After 8+ years consuming REST APIs on Android, I wanted to build one properly: with clean contracts, proper security, predictable error handling and production‑grade structure. 

This is not a minimal tutorial: every layer is separated, every edge case considered, every public surface follows REST conventions.

---

## Features

- JWT based authentication: user registration and login
- Full user account management
- Complete job lifecycle: create, read, update, delete
- Flexible job search and filtering
- Pagination on all collection endpoints
- Optional bookmark feature for users
- Strict layered architecture: Controller → Service → Repository
- PostgreSQL persistence via Spring Data JPA / Hibernate
- Centralised global exception handling → consistent JSON errors + correct HTTP status codes
- DTO‑first design: request / response models separated from internal entities
- Input validation on all incoming payloads

---

## Tech Stack

- Java 17+
- **Spring Boot 3**
- Spring Web (REST API)
- Spring Security + JWT
- Spring Data JPA
- Hibernate ORM
- PostgreSQL
- Maven
- Lombok

---

## Project Structure

com.mojtaba.jobboard\
├── controller\    # HTTP endpoints & request mapping  
├── service\    # Business logic, no web dependencies  
├── repository\    # Data access layer  
├── model\    # Internal JPA entities  
├── dto\    # Request / Response objects — never leak entities  
├── config\    # Bean configuration, security setup  
├── security\    # JWT filters, authentication logic  
├── exception\    # Custom exceptions + global handler  
└── JobBoardApplication.java  

---

## Authentication Flow

1. User registers `/auth/register`
2. User logs in `/auth/login`
3. Server returns JWT token
4. Token is used in Authorization header:

```http
Authorization: Bearer <token>
```
---

## API Endpoints
### Auth
| Method | Endpoint         | Description     |
| ------ | ---------------- | --------------- |
| POST   | `/auth/register` | Register user   |
| POST   | `/auth/login`    | Login & get JWT |

### Jobs
| Method | Endpoint     | Description    |
| ------ | ------------ | -------------- |
| GET    | `/jobs`      | Get all jobs   |
| GET    | `/jobs/{id}` | Get job by ID  |
| POST   | `/jobs`      | Create new job |
| PUT    | `/jobs/{id}` | Update job     |
| DELETE | `/jobs/{id}` | Delete job     |

Bookmarks (optional)
| Method | Endpoint             | Description        |
| ------ | -------------------- | ------------------ |
| POST   | `/bookmarks/{jobId}` | Bookmark a job     |
| GET    | `/bookmarks`         | Get user bookmarks |

---

## Setup & Run
### 1. Clone repository
```BASH
git clone https://github.com/<your-username>/jobboard.git
cd jobboard
```

### 2. Configure database
Edit `application.yml`:
```YAML
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jobboard
    username: root
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3. start  
```Bash
./mvnw spring-boot:run
```
or
```Bash
mvn spring-boot:run
```

---

## Project Goals  
+ Apply real‑world REST API design principles
+ Implement secure, industry‑standard authentication
+ Practice strict separation of concerns and clean architecture
+ Better understand the server side → build more resilient, easier‑to‑debug Android clients

---

## Possible Extensions  
+ Containerised deployment with Docker
+ Role‑based access control & admin area
+ Email verification flow
+ Deployment to Render / AWS
+ CI/CD pipeline

---

## Author
Mojtaba Shafaei
Senior Android Developer — using backend exploration to build better end‑to‑end systems.

---

## License
Open source — for study, reference and learning.
