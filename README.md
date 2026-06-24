# Nutrition-Tracking-API
## About the Project
A RESTful API built with Java and Spring Boot that allows users to track their daily meals
and nutritional intake. Users can register, login, and manage their own meal data securely through
JWT authentication.

Built for personal use and to practice enterprise Java backend development skills including
layered architecture, Spring Security, database relationships, input validation, and unit testing.

## Tech Stack
* [![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](https://www.java.com/en/)
* [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](https://spring.io/)
* [![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](https://www.postgresql.org/)

## Features
* JWT authentication and Spring Security
* BCrypt password hashing
* Full CRUD REST API for Meal and User entities
* User-Meal relationships with JPA
* Input validation
* Unit testing with JUnit 5 and Mockito
* Layered architecture (Controller → Service → Repository)

## Getting Started
### Prerequisites
To run this project locally, you will need the following installed:
* [Java 21+](https://www.oracle.com/java/technologies/downloads/)
* [Maven](https://maven.apache.org/download.cgi)
* [PostgreSQL](https://www.postgresql.org/download/)

### Installation
1. Clone the repository
```bash
git clone https://github.com/soohyunlee5/Nutrition-Tracking-API.git
```

2. Create a PostgreSQL database
```bash
psql -U postgres
CREATE DATABASE nutrition_db;
```

3. Create a new '.env' file in the root of the project and enter correct information
```
DB_URL=jdbc:postgresql://localhost:5432/nutrition_db
DB_USERNAME=yourusername
DB_PASSWORD=yourpassword
JWT_SECRET=yoursecretkey
```
**Note:** For `JWT_SECRET` use a 256-bit hex string. You can generate one by running:
```bash
openssl rand -hex 32
```

4. Run the application

Option 1 - IDE:
Open the project and run `NutritionApiApplication.java`

Option 2 - Command line (requires Maven in PATH):
```bash
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`

### Running Tests
```bash
mvn test
```
Or run the test classes directly from your IDE:
* 'MealServiceTest'
* `UserServiceTest'

### API Documentation
This project uses Swagger UI for interactive API documentation.

To view the documentation, open the following link after running the application:
* http://localhost:8080/swagger-ui/index.html

All endpoints can be tested directly from the Swagger UI. Protected endpoints require a JWT token.

To obtain a JWT token:
1. Register or login under 'auth-controller'
2. Fill in the request body with appropriate information
3. Execute to generate a token
4. Click 'Authorize' near the top of the page and paste the token into the field

## Acknowledgements
* [Spring Boot Documentation](https://spring.io/projects/spring-boot)
* [Swagger Documentation](https://swagger.io/docs/)
* [JWT.io](https://jwt.io/)
* [Img Shields](https://shields.io/)