## KevaBook Architecture Decisions and Technology Choices

# Introduction

KevaBook is a microservice-based booking platform designed to allow businesses and individuals to manage bookings efficiently and reliably.

The platform is designed using a distributed microservices architecture where each service is independently deployable, independently scalable, and owns its own database. The system follows modern cloud-native architectural principles using Spring Boot and Spring Cloud technologies.

The architecture was designed with the following goals:

* Scalability
* Maintainability
* Service independence
* Fault isolation
* Flexibility
* Future extensibility


# Microservices Architecture

KevaBook uses a microservices architecture instead of a monolithic architecture.

The platform is divided into multiple independent services:

* User Service
* Business Service
* Booking Service
* Notification Service
* API Gateway
* Eureka Discovery Service
* Config Service

Each service is responsible for a single business capability and can evolve independently.

## Why Microservices?

Microservices were chosen because:

* Services can scale independently
* Each service can use the most appropriate database technology
* Faults are isolated to individual services
* Teams can independently develop and deploy services
* Easier long-term maintainability
* Better alignment with cloud-native deployment patterns

The architecture also supports future expansion such as:

* Payment Service
* Analytics Service
* Recommendation Service
* Audit Service

without tightly coupling the system.


# API Gateway

KevaBook uses an API Gateway as the single entry point into the platform.

All client requests pass through the API Gateway before reaching internal services.

The API Gateway is responsible for:

* Request routing
* Authentication and authorization
* Security enforcement
* Rate limiting
* Centralized request filtering

## Why API Gateway?

Using an API Gateway provides several advantages:

* Internal services remain hidden from external clients
* Security is centralized
* Cross-cutting concerns are handled in one location
* Simplifies frontend integration
* Supports future API versioning and throttling

The gateway is implemented using Spring Cloud Gateway.


# Authentication and Authorization

The platform uses OAuth2 and Keycloak for authentication and authorization.

Authentication is centralized at the API Gateway layer.

## Why OAuth2 and Keycloak?

OAuth2 was selected because it is an industry-standard authorization framework widely used in distributed systems.

Keycloak provides:

* Centralized identity management
* JWT token generation and validation
* Role-based access control
* OAuth2 and OpenID Connect support
* User federation capabilities
* Scalable authentication infrastructure

This architecture avoids implementing custom authentication logic inside each microservice.


# Service Discovery

KevaBook uses Eureka Server for service discovery.

Each microservice registers itself with Eureka and can dynamically discover other services.

## Why Eureka?

Eureka eliminates the need for hardcoded service addresses.

Benefits include:

* Dynamic service registration
* Client-side load balancing
* Easier scaling
* Improved fault tolerance
* Better cloud-native support

This allows services to communicate using logical service names rather than fixed IP addresses.

Example:

http://userservice

instead of:

http://192.168.x.x:8081


# Inter-Service Communication

KevaBook uses REST over HTTP for synchronous service-to-service communication.

The platform uses Spring Boot RestClient and HTTP interfaces for communication between services.

## Why REST over HTTP?

REST over HTTP was selected because:

* Simple and widely adopted
* Easy integration with Spring Boot
* Human-readable JSON payloads
* Stateless communication model
* Easy debugging and monitoring
* Well-suited for request-response workflows

The system primarily performs transactional operations such as:

* Availability checks
* User validation
* Booking validation
* Business profile retrieval

which naturally fit synchronous REST communication.

## Implementation Approach

The architecture uses:

* Spring RestClient
* @HttpExchange interfaces
* Service discovery with Eureka
* Load-balanced HTTP communication

Example workflow:

Booking Service → Business Service → Availability Check

This communication is synchronous because an immediate response is required before continuing the booking workflow.


# Event-Driven Communication

KevaBook uses RabbitMQ for asynchronous communication and background event processing.

RabbitMQ acts as the event bus between services.

## Why RabbitMQ?

RabbitMQ was selected because the platform requires reliable asynchronous task processing rather than large-scale event streaming.

RabbitMQ is well-suited for:

* Notification processing
* Email delivery
* SMS processing
* Background jobs
* Workflow events
* Reliable message delivery

Examples of asynchronous events include:

* Booking Created
* Booking Approved
* Booking Rejected
* Notification Sent

## Why RabbitMQ Instead of Kafka?

Kafka was considered but RabbitMQ was chosen because KevaBook currently focuses on transactional workflows rather than high-volume event streaming.

RabbitMQ offers:

* Simpler operational setup
* Easier local development
* Lower infrastructure complexity
* Better suitability for task-based messaging
* Reliable delivery guarantees
* Easier integration for MVP-scale systems

Kafka is more appropriate for:

* Massive event streams
* Real-time analytics
* Event sourcing
* Log aggregation
* Stream processing systems

At the current stage of KevaBook, RabbitMQ provides the most practical balance between simplicity and reliability.


# Database Architecture

KevaBook follows the database-per-service pattern.

Each microservice owns and manages its own database independently.

This avoids tight coupling between services and supports service autonomy.

The platform also uses a polyglot persistence strategy, selecting the database technology best suited for each service.


# MongoDB for User Service

The User Service uses MongoDB.

## Why MongoDB?

MongoDB was selected because user profile data is flexible and may evolve frequently over time.

MongoDB provides:

* Flexible schema design
* JSON document storage
* Easy profile evolution
* Horizontal scalability
* Efficient handling of semi-structured data

User data may include:

* Profile metadata
* User preferences
* Role information
* Future extensible attributes

Since the User Service does not heavily depend on complex relational joins or transactional workflows, MongoDB is a suitable choice.


# PostgreSQL for Business, Booking, and Notification Services

The Business Service, Booking Service, and Notification Service use PostgreSQL.

## Why PostgreSQL?

These services require:

* Strong relational consistency
* Transactional integrity
* Structured relational data
* ACID compliance
* Referential integrity

The Booking Service especially requires reliable transactions to prevent inconsistent booking states or double booking scenarios.

PostgreSQL provides:

* Reliable ACID transactions
* Strong relational modeling
* Advanced querying capabilities
* Data consistency guarantees
* Mature production reliability

These properties make PostgreSQL highly suitable for booking and scheduling systems.


# Notification Architecture

The Notification Service handles:

* Email notifications
* SMS notifications
* Booking updates
* Delivery tracking

The service consumes asynchronous events from RabbitMQ.

## Notification Workflow

Example workflow:

1. Booking created
2. Booking event published
3. Notification Service consumes event
4. SMS/email notification sent

This architecture decouples notification processing from the main booking workflow and improves system responsiveness.


# Configuration Management

KevaBook uses a centralized Config Service.

## Why Config Service?

Centralized configuration provides:

* Externalized configuration management
* Environment-specific configurations
* Easier deployment management
* Centralized property updates

This simplifies configuration management across multiple services.


# Containerization and Deployment

The platform uses Docker for containerization.

## Why Docker?

Docker provides:

* Consistent environments
* Simplified deployment
* Service isolation
* Easier local development
* Better scalability support

The architecture is also designed to support future Kubernetes deployment for orchestration and scaling.


# Observability and Monitoring

The architecture plans future integration with observability tools such as:

* Zipkin
* Prometheus
* Grafana

## Purpose of Observability

Observability tooling will provide:

* Distributed tracing
* Metrics monitoring
* System health monitoring
* Performance analysis
* Failure diagnostics

This is especially important in distributed microservice environments where requests traverse multiple services.


# Frontend Architecture

The frontend is planned to use React.

React was selected because:

* Component-based architecture
* Strong ecosystem
* Good API integration support
* Suitable for scalable SPA applications

The frontend communicates exclusively through the API Gateway.


# Conclusion

KevaBook’s architecture was designed with scalability, maintainability, and extensibility in mind.

The platform combines:

* Microservices architecture
* REST-based synchronous communication
* Event-driven asynchronous processing
* Polyglot persistence
* Centralized security
* Service discovery
* Containerized deployment

This design provides a strong foundation for future growth while maintaining simplicity and practicality for the platform’s current requirements.

