🚀 Microservices Security Project (Gateway + JWT + Roles)

This project is built using a Spring Boot microservices architecture and consists of the following components:

- 🧭 Gateway Service (Spring Cloud Gateway)
- 👤 User Service (Authentication + JWT + Roles)
- 📦 Product Service (Protected API with Role-based Access)

---

🧠 Project Purpose

The main goals of this project are:

- ✅ Authentication using JWT (identify the user)
- ✅ Token validation at the Gateway level
- ✅ Role-based Authorization (ADMIN / USER)
- ✅ Secure communication between microservices

---

🏗️ Architecture

Client → Gateway → Services

- All requests first go to the Gateway
- Gateway validates the JWT token
- If the token is valid:
  - "userId" and "role" are extracted
  - added to request headers
- Then the request is forwarded to the appropriate service

---

🔐 Authentication & Authorization Flow

1. Register / Login

The user registers or logs in:

POST /auth/register
POST /auth/login

---

2. JWT Token Generation

User Service generates a token containing:

{
  "userId": 1,
  "role": "ADMIN"
}

---

3. Client Sends Request

Authorization: Bearer <JWT_TOKEN>

---

4. Gateway Validates Token

Gateway performs:

- Token parsing
- Signature validation
- Expiration check

---

5. Gateway Adds Headers

X-User-Id: 1
X-Role: ADMIN

---

6. Service Performs Authorization

Product Service makes decisions based on roles:

- "ADMIN" → full access
- "USER" → limited access

---

📁 Services

👤 User Service

- Handles Register and Login
- Generates JWT tokens
- Assigns roles

Endpoints:

- "POST /auth/register"
- "POST /auth/login"

---

🧭 Gateway Service

- Validates JWT using GlobalFilter
- Extracts userId and role

Main Responsibilities:

- Checks Authorization header
- Parses token
- Extracts "userId" and "role"
- Adds headers:
  X-User-Id
X-Role

---

📦 Product Service

- Protected API
- Implements role-based access control

Endpoints:

- "GET /product" → USER & ADMIN
- "POST /product" → ADMIN only

---

⚙️ Technologies

- Java 17+
- Spring Boot
- Spring Security
- Spring Cloud Gateway
- JWT (jjwt)
- Maven

---

🧪 Testing (with Postman)

1. Login

POST /auth/login

Response:

{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}

---

2. Send Request

POST /product

Header:

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

---

🔐 Role-based Access

Endpoint| USER| ADMIN
GET /product| ✅| ✅
POST /product| ❌| ✅

---

❗ Possible Errors

🔴 401 Unauthorized

Causes:

- Missing token
- Expired token
- Invalid secret key
- Incorrect header format

---

🔴 403 Forbidden

Cause:

- User role does not have permission

---

🔑 Important Notes

- All services must use the same secret key
- Token must be sent with "Bearer " prefix
- Gateway handles authentication, but authorization can also be enforced at the service level

---

🚀 Future Improvements

- Refresh token mechanism
- Session management with Redis
- Centralized logging
- API rate limiting

---

👨‍💻 Author

Developed by Arzu 🚀
