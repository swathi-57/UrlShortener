# 🔗 URL Shortener - Spring Boot Project

🚀 A scalable **URL Shortener service** built using **Java, Spring Boot, and MySQL** that converts long URLs into short, shareable links.

This project demonstrates **backend engineering concepts**, including:

- RESTful API design
- Database modeling
- Scalable ID generation
- Modular Spring Boot architecture

---

# 📌 Problem Statement

Long URLs are difficult to share and remember.

Example:
https://www.example.com/blog/backend-development/spring-boot-url-shortener-tutorial

Using this application, the long URL becomes:
http://localhost:8080/aB3d


When users access the short link, they are **automatically redirected to the original URL**.

---

# ✨ Features

✅ Convert long URLs into short links  
✅ Redirect short URLs to original URLs  
✅ RESTful backend APIs  
✅ Base62 encoding for short link generation  
✅ MySQL database integration  
✅ Scalable backend architecture  

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|--------|
| **Java** | Core programming language |
| **Spring Boot** | Backend framework |
| **Spring Data JPA** | Database interaction |
| **MySQL** | Persistent storage |
| **Maven** | Dependency management |
| **Git & GitHub** | Version control |

---

# 🏗 System Architecture

```text
        User
         |
         v
   Frontend / Postman
         |
         v
 Spring Boot Controller
         |
         v
      Service Layer
   (Business Logic)
         |
         v
    Repository Layer
        (JPA)
         |
         v
     MySQL Database

```

# ⚙️ How the System Works

## Step 1: User submits a long URL

Example request:

POST /url/shorten 

Request Body:https://google.com


---

## Step 2: Backend generates a short code

The system generates a **unique ID** and converts it using **Base62 encoding**.

Example:

Database ID = 125
Base62 = b7


---

## Step 3: Store mapping in database

| ID | Long URL | Short URL |
|----|---------|-----------|
| 125 | https://google.com | b7 |

---

## Step 4: Access short URL

User visits:
http://localhost:8080/b7


Backend fetches original URL from database and **redirects the user**.

---

# 🖼 URL Shortening Flow Diagram

This diagram explains how short URLs are generated.
```text
User enters Long URL
        |
        v
Generate Unique ID
        |
        v
Convert ID → Base62 Encoding
        |
        v
Store in Database
        |
        v
Return Short URL
```
Long URL
https://google.com

Generated ID
125

Base62 Encoding
b7

Short URL
http://localhost:8080/b7

---

## High-Level Architecture Diagram

```text

           +-------------+
           |   User      |
           +-------------+
                  |
                  v
          +----------------+
          |   REST API     |
          | Spring Boot    |
          +----------------+
           |            |
           v            v
   +-------------+   +------------+
   |  URL Service|   | Rate Limit |
   +-------------+   +------------+
           |
           v
     +-----------+
     |  Database |
     |  MySQL    |
     +-----------+
```
Explanation :

- User sends a long URL request
- Spring Boot REST API receives request
- Service layer generates short URL
- Mapping stored in MySQL database
- Short URL returned to user
---
# 📂 Project Structure

```text
url-shortener
│
├── controller
│ UrlController.java
│
├── service
│ UrlService.java
│
├── repository
│ UrlRepository.java
│
├── model
│ UrlMapping.java
│
├── util
│ Base62Encoder.java
│
└── UrlShortenerApplication.java
```

---

# 🔌 API Endpoints

## 1️⃣ Shorten URL

POST /url/shorten


Example Request : 
https://example.com/long/url


Response : aB3d

---

## 2️⃣ Redirect to Original URL

GET /url/{shortCode}

Example  : GET /url/aB3d


Response:

➡ Redirects to original URL.

---

# 🗄 Database Schema

Table: `url_mapping`

| Column | Type |
|------|------|
| id | BIGINT |
| long_url | VARCHAR |
| short_url | VARCHAR |

---

# 🚀 How to Run the Project

### 1️⃣ Clone repository
git clone :  https://github.com/yourusername/url-shortener.git


---

### 2️⃣ Navigate to project
cd url-shortener

---

### 3️⃣ Configure MySQL

Update `application.properties`

spring.datasource.url=jdbc:mysql://localhost:3306/urlshortener
spring.datasource.username=root
spring.datasource.password=yourpassword


---

### 4️⃣ Run application

mvn spring-boot:run

Server starts at:  http://localhost:8080


---

# 📈 Future Improvements

🔹 Redis caching for faster lookups  
🔹 Rate limiting to prevent abuse  
🔹 Analytics dashboard for URL clicks  
🔹 Custom short URLs  
🔹 URL expiration feature  
🔹 Distributed ID generation  

---

# 🎯 Learning Outcomes

This project helped me understand:

- Backend architecture
- REST API design
- Database optimization
- URL encoding techniques
- Scalable system design fundamentals

---

# 👩‍💻 Author

**Swathi Mittapalli**

📧 Email: mittapalliswathi57@gmail.com  
💼 LinkedIn: https://linkedin.com/in/swathi-mittapalli  
💻 GitHub: https://github.com/swathi-57










