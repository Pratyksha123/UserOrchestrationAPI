# User Orchestration API

## Overview

The User Orchestration API is a Spring Boot application that provides RESTful endpoints for managing user data. It includes functionalities to search users by email and search users by name or SSN prefix.

## Features

- Fetch a user by email.
- Search users by name or SSN prefix.

## Technologies Used

- Java
- Spring Boot
- Maven
- H2 Database
- RestTemplate
- Swagger

## Dataset
The user data is fetched from DummyJSON and loaded into the in-memory H2 database.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Pratyksha123/user-orchestration-api.git
    cd user-orchestration-api
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### API Endpoints

#### Get User by Email

- **URL:** `/user/getByEmail`
- **Method:** `GET`
- **Description:** Fetches a user based on their email.
- **Parameters:**
  - `email` (query parameter): The email of the user to fetch.

#### Search Users by Name or SSN Prefix

- **URL:** `/user/searchByNamePrefix`
- **Method:** `GET`
- **Description:** Searches for users based on their name or SSN prefix.
- **Parameters:**
  - `prefix` (query parameter): The prefix to search for.
 
### Clean Code Practices Implemented

1.Modular Structure - Code is structured into layers: Controller, Service, Repository.

2.Application Logging using slf4j

3.Exception Handling - Global exception handler to manage errors gracefully.

4.Environment Layering - Config parameters externalized in application.properties.

5.Input Data Validations - Validations for input parameters.

6.Unit Testing & Code Coverage - JUnit test cases to ensure robustness.

7.Optimized API Calls - Efficient handling of third-party API responses.

8.Resilience Mechanisms - Implemented retry mechanisms for external API failures.

9.REST Standards - Properly designed REST endpoints with standard HTTP methods.

10.API Documentation - OpenAPI/Swagger documentation for easy API acces

### H2 Database Access

Console URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: password

### Notes

The API fetches user data on demand and stores it in H2.

Data is lost when the application restarts, as H2 is an in-memory database.

External API calls have a retry mechanism for resilience.

### Running Tests

To run the tests, use the following command:
```sh
mvn test
