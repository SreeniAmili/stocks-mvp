# Stocks-MVP

## 📌 Overview

Stocks-MVP is a **Spring Boot-based REST API** for managing stocks. It provides endpoints to perform CRUD operations on stocks, secured with **Spring Security** and documented using **Swagger (OpenAPI)**.

## 🏗️ Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA** (PostgreSQL)
- **Spring Security** (Basic Auth)
- **HikariCP** (Connection Pooling)
- **Swagger OpenAPI** (API Documentation)
- **Docker & Podman**
- **JUnit 5 & Mockito** (Unit Tests)

## 🚀 Features

- **CRUD Operations** for managing stocks
- **Secure Endpoints** with Basic Authentication
- **Pagination & Sorting** for stock listings
- **PostgreSQL Database Integration**
- **Docker Support** for deployment
- **Automated API Documentation** using Swagger

---

## ⚙️ Installation & Setup

### **1️⃣ Clone the Repository**

```bash
git clone <repo-link>
cd stocks-mvp
```

### **2️⃣ Configure PostgreSQL**

Make sure PostgreSQL is running and create the required database:

```sql
CREATE DATABASE stocks_db;
```

### **3️⃣ Update **``

Modify `src/main/resources/application.yml` with your PostgreSQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/stocks_db
    username: postgres
    password: password
```

### **4️⃣ Build & Run the Application**

Run the following command to build the project and start the application:

```bash
mvn clean install
mvn spring-boot:run
```

### **5️⃣ Access the Application**

- **Swagger UI** → [`http://localhost:8081/swagger-ui.html`](http://localhost:8081/swagger-ui/index.html#)
- **API Docs (JSON)** → [`http://localhost:8081/v3/api-docs`](http://localhost:8081/v3/api-docs)

---

## 📦 Running with Docker

### **1️⃣ Build the Docker Image**

```bash
docker build -t stocks-mvp .
```

### **2️⃣ Run the Container**

```bash
docker run -p 8081:8081 --name stocks-app stocks-mvp
```

---

## 🔑 Authentication

The API uses **Basic Authentication**:

- **Username:** `admin`
- **Password:** `admin123`

---

## 🛠️ API Endpoints

### **📌 Stock Management**

| Method   | Endpoint           | Description                |
| -------- | ------------------ | -------------------------- |
| `GET`    | `/api/stocks`      | Get all stocks (Paginated) |
| `GET`    | `/api/stocks/{id}` | Get stock by ID            |
| `POST`   | `/api/stocks`      | Create a new stock         |
| `PATCH`  | `/api/stocks/{id}` | Update stock price         |
| `DELETE` | `/api/stocks/{id}` | Delete stock               |

---

## 🧪 Running Tests

Run unit tests using:

```bash
mvn test
```

---

## 🛠️ Troubleshooting

**1. Application Fails to Start?**\
Ensure PostgreSQL is running:

```bash
psql -U postgres -d stocks_db
```

**2. Unable to Connect to PostgreSQL in Podman?**\
Use `host.containers.internal` in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://host.containers.internal:5432/stocks_db
```


