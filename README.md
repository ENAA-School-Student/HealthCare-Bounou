# HealthCare-Bounou

A healthcare management system built with Spring Boot. Manage patients, doctors, medical records, and appointments with ease.

## Tech Stack

- Java 21 | Spring Boot 4.0.6 | MySQL | JPA/Hibernate
- MapStruct | Flyway | Swagger | Docker

## Quick Start

### Prerequisites
- Java 21+
- Maven
- MySQL

### Setup

1. **Clone and navigate**
   ```bash
   cd HealthCare-Bounou
   ```

2. **Update database config** (`src/main/resources/application.properties`)
   ```properties
   spring.datasource.password=your_password
   ```

3. **Build & Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   API at `http://localhost:8080`

### Using Docker
```bash
docker-compose up --build
```

## API Docs

View at `http://localhost:8080/swagger-ui.html`

## Diagrams

<img width="1753" height="1382" alt="Use Case Diagram" src="https://github.com/user-attachments/assets/cebbbd97-69a8-4481-85bf-9815b2f8089a" />

<img width="1041" height="628" alt="Class Diagram" src="https://github.com/user-attachments/assets/c1314a21-64a4-4808-b1e6-07620caf274b" />

<img width="499" height="524" alt="Sequence Diagram" src="https://github.com/user-attachments/assets/474eebd0-2c14-4df9-8228-f3eda5ca9311" />
