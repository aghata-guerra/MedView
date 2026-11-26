# MedView API (backend)

REST API that supports clinical triage of patients in the diabetes and hypertension risk groups.
Built with Java 21, Spring Boot, JPA and H2, following the **MVC pattern** in layers.

## Run

```
cd backend
./mvnw spring-boot:run
```

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:file:./data/medview-db`, user `sa`, empty password)

## Tests

```
./mvnw test
```

## Architecture (MVC)

```
com.guerra.medview
├── controller     C - receives the HTTP request, validates the body, calls the service, returns the status code
├── service        business rules, transactions
├── repository     data access (Spring Data JPA)
├── model          M - JPA entities: Patient, Address, Doctor
├── security       JWT issuing (JwtService)
├── dto            V - the JSON the client sends and receives (records)
├── exception      domain exceptions and the global handler that turns them into HTTP responses
└── config         Spring Security (stateless JWT) and OpenAPI (Swagger) configuration
```

In a REST API the **View** is the JSON representation of the data, built here by the DTOs
(`PatientRequest`, `PatientResponse`, ...). The Angular front end renders it for the user.

Request flow: `Controller -> Service -> Repository -> Model (database)`, and back as a DTO.

## Authentication

Every endpoint except `/api/v1/auth/**`, Swagger and the H2 console requires `Authorization: Bearer <token>`.
Doctors register (CPF validated, password stored as a BCrypt hash) and log in to get a JWT (HS256).
In Swagger UI, call `POST /api/v1/auth/login`, copy the token and press **Authorize**.

The signing secret comes from the `JWT_SECRET` environment variable (at least 32 characters).
`application.yml` has a development default; set your own outside development.

## Endpoints

| Method | Path                    | Success | Errors   |
|--------|-------------------------|---------|----------|
| POST   | `/api/v1/auth/register` | 201 (public) | 400, 409 |
| POST   | `/api/v1/auth/login`    | 200 + token (public) | 400, 401 |
| POST   | `/api/v1/patients`      | 201 + `Location` header and body | 400 |
| GET    | `/api/v1/patients/{id}` | 200     | 404      |
| PUT    | `/api/v1/patients/{id}` | 204     | 400, 404 |
| DELETE | `/api/v1/patients/{id}` | 204     | 404      |

The patient endpoints also answer **401** when the token is missing or invalid.

Example body:

```json
{
  "name": "Maria Silva",
  "address": { "street": "Rua da Aurora, 100", "city": "Recife", "state": "PE" }
}
```
