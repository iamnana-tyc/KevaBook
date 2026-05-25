# User Service

The User Service is a Spring Boot microservice responsible for managing users within the system.

It provides REST APIs for:

- Creating users
- Retrieving a user by ID
- Retrieving paginated users
- Updating user information
- Deleting users

This service is part of a microservices architecture and is designed to operate independently.


# Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data MongoDB
- Spring Validation
- Lombok
- Maven
- OpenAPI / Swagger


# Features

- Create User
- Get User By ID
- Get All Users with Pagination
- Partial User Update
- Delete User
- Request Validation
- Exception Handling
- MongoDB Integration
- OpenAPI Documentation


# Project Structure

```text
src/main/java/com/example/userservice
│
├── controller       # REST Controllers
├── service          # Business Logic
├── repository       # MongoDB Repositories
├── model            # MongoDB Documents
├── dto              # Request & Response DTOs
├── mapper           # Entity Mapping
├── exception        # Custom Exceptions
├── config           # Application Configurations
```


# API Base URL

```text
/api/v1/users
```

---

# API Endpoints

## Create User

### Request

```http
POST /api/v1/users
```

### Request Body

```json
{
  "firstName": "Nana",
  "lastName": "Owusu",
  "email": "nana@example.com"
}
```

### Response

```json
{
  "id": "68302ab7e1e0f95c1c27f111",
  "firstName": "Nana",
  "lastName": "Owusu",
  "email": "nana@example.com"
}
```


## Get User By ID

### Request

```http
GET /api/v1/users/{userId}
```

### Example

```http
GET /api/v1/users/68302ab7e1e0f95c1c27f111
```


## Get All Users

Supports:

- Pagination
- Sorting
- Sorting direction

### Request

```http
GET /api/v1/users?pageNumber=0&pageSize=10&sortBy=firstName&sortOrder=asc
```


## Partial Update User

### Request

```http
PATCH /api/v1/users/{userId}
```

### Request Body

```json
{
  "firstName": "Updated Name"
}
```


## Delete User

### Request

```http
DELETE /api/v1/users/{userId}
```

---

# Validation

The service uses Jakarta Validation for validating incoming requests.

Example validations include:

- Required fields
- Non-empty fields
- Email validation

---

# Pagination

The service supports pagination using query parameters:

| Parameter   | Description              |
|-------------|--------------------------|
| pageNumber  | Current page number      |
| pageSize    | Number of records/page   |
| sortBy      | Field to sort by         |
| sortOrder   | asc or desc              |


# Running the Application

## Prerequisites

- Java 21
- Maven
- MongoDB


## Clone Repository

```bash
git clone <repository-url>
```

## Navigate to Service

```bash
cd userservice
```


## Run Application

```bash
./mvnw spring-boot:run
```

Or:

```bash
mvn spring-boot:run
```

# Build Application

```bash
mvn clean install
```

# Swagger / OpenAPI Documentation

After running the application:

```text
http://localhost:8080/swagger-ui.html
```

or

```text
http://localhost:8080/swagger-ui/index.html
```


# MongoDB Configuration

Example configuration:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/userdatabase
```

# Error Handling

The service uses custom exceptions such as:

- ResourceNotFoundException
- APIException

These provide meaningful API error responses.


# Development Notes

This service follows a layered architecture:

```text
Controller → Service → Repository → Database
```

Business logic is handled in the service layer while controllers remain thin.


# Author

Nana Owusu
````
