# Employee Management System

A full-stack Employee Management application built with **Spring Boot** (Java 21) 
and **Angular**, backed by **PostgreSQL**. Designed with production-grade patterns 
including a layered architecture, input validation, structured logging, 
unit tests, and environment-based configuration.

## Tech Stack

**Backend**
- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5 + Mockito

**Frontend**
- Angular 17+
- TypeScript
- Angular Reactive Forms
- Angular HttpClient
- Environment-based configuration

## Features

- View all employees in a searchable, filterable table
- Add a new employee with full form validation
- Edit an existing employee's details
- Delete an employee with confirmation
- Search employees by first or last name
- Clean error responses for invalid or missing data
- Structured logging across all key operations

## Architecture

The backend follows a strict three-layer architecture:
