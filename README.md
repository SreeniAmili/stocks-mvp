# Stocks Management API

## 📌 Project Overview
This is a **Spring Boot-based Stock Management API** that allows users to manage stocks, including creating, updating, retrieving, and deleting stock information.

### **🚀 Technologies Used**
- **Java 17**
- **Spring Boot 3.x** (REST API, JPA, Hibernate)
- **PostgreSQL** (Database)
- **Lombok** (Boilerplate code reduction)
- **Spring Security** (Basic authentication)
- **Testcontainers** (Integration testing with PostgreSQL)
- **Swagger OpenAPI** (API Documentation)
- **Docker & Docker Compose** (Deployment)

---

## 🔧 **Project Setup**
### **📜 Prerequisites**
Make sure you have the following installed:
- **Java 17** (Check with `java -version`)
- **Maven** (Check with `mvn -version`)
- **Docker** (Check with `docker --version`)

### **📌 Steps to Set Up Locally**
1️⃣ **Clone the Repository**
```bash
 git clone https://github.com/your-repo/stocks-mvp.git
 cd stocks-mvp
```

2️⃣ **Build the Project**
```bash
mvn clean package -DskipTests
```

3️⃣ **Run the Application Locally**
```bash
mvn spring-boot:run
```
The API will now be available at:
```bash
http://localhost:8082
```

4️⃣ **Access Swagger API Documentation**
```bash
http://localhost:8082/swagger-ui/index.html
```

---

## 📦 **Docker Deployment**
You can deploy this project in **two ways:**
1. **Using Docker Compose** (recommended for managing both Spring Boot & PostgreSQL together)
2. **Running Containers Manually** (if you prefer not using Docker Compose)

### **1️⃣ Option 1: Deploy with Docker Compose**
```bash
docker-compose up --build -d
```
✅ This will start **both PostgreSQL and the Stocks API** in Docker.

To stop the services:
```bash
docker-compose down
```

### **2️⃣ Option 2: Deploy Manually Without Docker Compose**
If you prefer running Docker containers manually, follow these steps:

1️⃣ **Start PostgreSQL in Docker**
```bash
docker run -d --name postgres_container -p 5432:5432 \
  -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=stocks_db \
  postgres:15
```

2️⃣ **Build and Run the Spring Boot App**
```bash
docker build -t stocks-app .
docker run -d --name stocks_app -p 8082:8082 stocks-app
```

✅ **Now, Swagger UI will be available at:**
```
http://localhost:8082/swagger-ui/index.html
```

---

## 🧪 **Running Tests**
### **1️⃣ Run Unit Tests**
```bash
mvn test
```
### **2️⃣ Run Integration Tests (with Testcontainers)**
```bash
mvn verify
```

---

## 📡 **API Endpoints**
### **Authentication**
| Method | Endpoint             | Description  |
|--------|----------------------|--------------|
| `GET`  | `/api/stocks`        | Get all stocks |
| `GET`  | `/api/stocks/{id}`   | Get stock by ID |
| `POST` | `/api/stocks`        | Create a new stock |
| `PATCH`| `/api/stocks/{id}`   | Update stock price |
| `DELETE`| `/api/stocks/{id}`  | Delete a stock |

### **Example Request: Create Stock**
```bash
curl -X POST "http://localhost:8082/api/stocks" \
     -H "Content-Type: application/json" \
     -d '{"name": "Tesla", "currentPrice": 900.50, "lastUpdate": "2025-03-16T12:00:00Z"}'
```

✅ **Expected Response (201 Created)**
```json
{
  "id": 1,
  "name": "Tesla",
  "currentPrice": 900.50,
  "lastUpdate": "2025-03-16T12:00:00Z"
}
```

---

## 🔑 **Security (Basic Authentication)**
📌 **Login Credentials:**
- **Username:** `admin`
- **Password:** `admin123`

All protected endpoints require authentication. Use the above credentials for accessing secured APIs.

---

## 🔄 **Troubleshooting**
### **🔹 Docker Build Fails (`no such file or directory` error)**
- Ensure that `Dockerfile` exists with **correct casing (`Dockerfile`, not `DockerFile`)**.
- Run Docker build again:
```bash
docker build -t stocks-app .
```

### **🔹 PostgreSQL Connection Issues in Docker**
- Ensure the database is running inside Docker:
```bash
docker logs postgres_container
```
- Try restarting Docker:
```bash
docker-compose down && docker-compose up -d
```

### **🔹 Testcontainers Fails to Start PostgreSQL**
- Ensure Docker is running (`docker ps`).
- Make sure Testcontainers is set up correctly in tests:
```java
static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
    .withDatabaseName("test_stocks_db")
    .withUsername("testuser")
    .withPassword("testpass");
```