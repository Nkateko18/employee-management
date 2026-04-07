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
Controller -> Service -> Repository
- **Controller** — handles HTTP requests and responses only
- **Service** — owns all business logic and validation rules
- **Repository** — the only layer that communicates with the database

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL
- Node.js 18+
- Angular CLI

### Backend Setup

1. Create a PostgreSQL database called `employee_db`
2. Navigate to the backend folder:
```bash
   cd backend
```
3. Update `src/main/resources/application.properties` with your 
   PostgreSQL credentials
4. Run the application:
```bash
   mvn spring-boot:run
```
5. The API will be available at `http://localhost:8080/api/employees`

### Frontend Setup

1. Navigate to the frontend folder:
```bash
   cd frontend
```
2. Install dependencies:
```bash
   npm install
```
3. Start the development server:
```bash
   ng serve
```
4. Open your browser at `http://localhost:4200`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| GET | `/api/employees/search?query=` | Search by name |
| POST | `/api/employees` | Create new employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

## What I Learned

This project was built to demonstrate production-ready full-stack development 
practices including layered architecture separation, DTO pattern, Bean Validation, 
global exception handling, structured logging with SLF4J, and Angular 
environment configuration for multi-target deployment.
