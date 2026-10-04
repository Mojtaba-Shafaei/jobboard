# Job Board REST API — Spring Boot Backend

Personal R&D project — built to deepen full-stack architecture understanding, so I can design and build **more robust mobile ↔ server integrations as a Senior Android Developer**.

After 8+ years consuming REST APIs on Android, I wanted to build one properly: with clean contracts, proper security, predictable error handling and production-grade structure.

This is not a minimal tutorial: every layer is separated, every edge case considered, every public surface follows REST conventions.

---

## Features

- JWT authentication: login and **server-side logout** — revoked tokens are blacklisted in the database and rejected with `401`
- Complete job lifecycle: create, read, update, delete
- Pagination on the jobs collection (`?page=0&size=10`)
- Strict layered architecture: Controller → Service → Repository
- H2 in-memory database by default (zero setup), PostgreSQL-ready via config
- Centralised global exception handling → consistent JSON errors + correct HTTP status codes
- DTO-first design: request / response models separated from internal entities
- Input validation on all incoming payloads (`@Valid`)
- Automated tests for the web layer (JUnit 5 + MockMvc + Mockito)

---

## Tech Stack

- Java 26
- **Spring Boot 4**
- Spring Web (REST API)
- Spring Security + JWT (jjwt)
- Spring Data JPA / Hibernate
- H2 (default) / PostgreSQL
- Jackson 3
- Maven
- Lombok
- Testing: JUnit 5, Spring MockMvc, Mockito

---

## Project Structure

```
com.mojtaba.jobboard
├── config/             # Bean configuration (Jackson)
│   └── security/       # Security chain, JWT service & filter, demo-user seeding
├── controller/         # HTTP endpoints & request mapping
├── dto/                # Request / Response objects — never leak entities
├── exception/          # Custom exceptions + global handler
├── mapper/             # Entity ↔ DTO mapping
├── model/              # Internal JPA entities
├── repository/         # Data access layer (Spring Data JPA)
├── service/            # Business logic, no web dependencies
└── JobboardApplication.java
```

---

## Authentication Flow

1. The server seeds demo users on first startup (see [Setup & Run](#setup--run))
2. User logs in via `POST /api/auth/login`
3. Server returns a JWT token
4. Token is sent on every protected request:

```http
Authorization: Bearer <token>
```

5. User logs out via `POST /api/auth/logout` — the token is stored in a
   `revoked_tokens` table (with its natural expiry) and rejected from then on.
   Expired rows are purged automatically, so the blocklist stays bounded.

---

## API Endpoints

Base URL: `http://localhost:8080`

### Auth — public (`/api/auth/**`)

| Method | Endpoint            | Description                  |
| ------ | ------------------- | ---------------------------- |
| POST   | `/api/auth/login`   | Login & get JWT              |
| POST   | `/api/auth/logout`  | Revoke current token (401 afterwards) |

### Jobs — requires `Authorization: Bearer <token>`

| Method | Endpoint        | Description                                     |
| ------ | --------------- | ----------------------------------------------- |
| GET    | `/api/jobs`     | Get all jobs — paginated: `?page=0&size=10`     |
| GET    | `/api/jobs/{id}`| Get job by ID                                   |
| POST   | `/api/jobs`     | Create new job                                  |
| PUT    | `/api/jobs/{id}`| Update job                                      |
| DELETE | `/api/jobs/{id}`| Delete job                                      |

### Error responses

Failures return a consistent JSON shape with the right status code:

```json
{
  "timestamp": "2026-10-04T21:15:30.123",
  "status": 401,
  "error": "Unauthorized",
  "message": "Missing or invalid token",
  "path": "/api/jobs",
  "method": "GET",
  "details": []
}
```

---

## Testing

```bash
./mvnw test
```

The web-layer tests are fast and self-contained: they use **standalone MockMvc** with a
**mocked service layer**, so they need no database and no full Spring context.

| Test | Endpoint | What it verifies |
| ---- | -------- | ---------------- |
| `getAllJob_returnsPaginatedJobs` | `GET /api/jobs` | `page`/`size` params, `Page` JSON (`content`, `totalElements`, `totalPages`), correct `PageRequest` passed to the service |
| `createJob_returnsSavedJob` | `POST /api/jobs` | JSON body → `JobRequest` binding, service delegation, response body |
| `getJobById_returnsJob` | `GET /api/jobs/{id}` | Path variable binding, `200` + job JSON |
| `deleteJob_deletesAndReturnsOk` | `DELETE /api/jobs/{id}` | `200` and service called with the right id |
| `updateJob_returnsUpdatedJob` | `PUT /api/jobs/{id}` | Path variable + body binding, service called with id and payload |

Plus `JobboardApplicationTests.contextLoads`, which boots the full Spring context against
in-memory H2 to prove the wiring (security, JPA, filters) is correct.

Run everything, or a single class:

```bash
./mvnw test                          # full suite
./mvnw test -Dtest=JobControllerTest # one class
```

---

## Setup & Run

### 1. Clone repository

```bash
git clone https://github.com/Mojtaba-Shafaei/jobboard.git
cd jobboard
```

### 2. Configure database

Out of the box it runs on **in-memory H2 — nothing to install** (JDBC URL
`jdbc:h2:mem:jobboard`). The H2 web console is enabled in `application.yml`; the security
chain currently guards every route outside `/api/auth/**`, so reaching it needs an extra
`permitAll` rule.

To use PostgreSQL instead, edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jobboard
    username: postgres
    password: your_password
```

### 3. Start

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

### 4. Try it

Demo users seeded on first run: `mojtaba` / `1234` and `admin` / `admin1234`.

```bash
# login
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"mojtaba","password":"1234"}' | jq -r .token)

# call a protected endpoint (paginated)
curl "http://localhost:8080/api/jobs?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"

# logout — this token is now blacklisted
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer $TOKEN"
```

---

## Project Goals

- Apply real-world REST API design principles
- Implement secure, industry-standard authentication
- Practice strict separation of concerns and clean architecture
- Better understand the server side → build more resilient, easier-to-debug Android clients

---

## Possible Extensions

- User registration endpoint
- Job search & filtering
- Bookmark feature for users
- Refresh tokens / token rotation
- Role-based access control & admin area
- Email verification flow
- Containerised deployment with Docker
- CI/CD pipeline + deployment to Render / AWS

---

## Author

Mojtaba Shafaei
Senior Android Developer — using backend exploration to build better end-to-end systems.

---

## License

Open source — for study, reference and learning.
