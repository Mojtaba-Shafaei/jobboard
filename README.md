# 💼 Job Board API

A backend REST API for a Job Board platform built with **Spring Boot 4**, featuring authentication, job management, and clean layered architecture.  
This project is designed for learning backend development and as a portfolio project.

---

## 🚀 Features

- 🔐 JWT Authentication (Register / Login)
- 👤 User management
- 💼 Job CRUD operations
- 🔍 Job search & filtering
- ⭐ Bookmark jobs (optional extension)
- 📄 Pagination support
- 🧱 Clean layered architecture (Controller → Service → Repository)
- 🗄️ PostgreSQL integration with JPA/Hibernate
- ⚠️ Global exception handling

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 4
- Spring Web (REST API)
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok

---

## 📦 Project Structure
com.mojtaba.jobboard\
├── controller\
├── service\
├── repository\
├── model\
├── dto\
├── config\
├── security\
├── exception\
└── JobBoardApplication.java  

---

## 🔐 Authentication Flow

1. User registers `/auth/register`
2. User logs in `/auth/login`
3. Server returns JWT token
4. Token is used in Authorization header:

```http
Authorization: Bearer <token>
```
---

## 📡 API Endpoints
### 🔑 Auth
| Method | Endpoint         | Description     |
| ------ | ---------------- | --------------- |
| POST   | `/auth/register` | Register user   |
| POST   | `/auth/login`    | Login & get JWT |

### 💼 Jobs
| Method | Endpoint     | Description    |
| ------ | ------------ | -------------- |
| GET    | `/jobs`      | Get all jobs   |
| GET    | `/jobs/{id}` | Get job by ID  |
| POST   | `/jobs`      | Create new job |
| PUT    | `/jobs/{id}` | Update job     |
| DELETE | `/jobs/{id}` | Delete job     |

⭐ Bookmarks (optional)
| Method | Endpoint             | Description        |
| ------ | -------------------- | ------------------ |
| POST   | `/bookmarks/{jobId}` | Bookmark a job     |
| GET    | `/bookmarks`         | Get user bookmarks |

---

## ⚙️ Setup & Run
### 1️⃣ Clone repository
```BASH
git clone https://github.com/<your-username>/jobboard.git
cd jobboard
```

### 2️⃣ Configure database
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

### 3️⃣ Run application
```Bash
./mvnw spring-boot:run
```
or
```Bash
mvn spring-boot:run
```

---

## 📌 Goals of this Project
+ Learn real-world Spring Boot architecture
+ Practice REST API design
+ Implement secure authentication (JWT)
+ Build portfolio-ready backend project

---

## 🧠 Future Improvements
+ Docker support 🐳
+ Role-based admin panel
+ Email verification
+ Cloud deployment (Render / AWS)
+ CI/CD pipeline

---

## 👨‍💻 Author
Mojtaba Shafaei
Backend Developer (Spring Boot learning project) + Senior Android Developer

---

## 📜 License
This project is open-source and available for learning purposes.
