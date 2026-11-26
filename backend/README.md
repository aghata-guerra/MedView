# MedView API (backend)

REST API that supports clinical triage of patients in the diabetes and hypertension risk groups.
Built with Java 21, Spring Boot, JPA and H2, following **hexagonal architecture** (ports and adapters).

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

## Architecture

```
com.guerra.medview.application
├── core                      business rules, no Spring inside
│   ├── domain                Patient, Address
│   ├── exception             PatientNotFoundException
│   └── usecase               RegisterPatient, FindPatientById, UpdatePatient, DeletePatientById
├── ports
│   ├── in                    what the application offers (implemented by the use cases)
│   └── out                   what the application needs (implemented by the adapters)
├── adapters
│   ├── in/controller         REST: controller, request/response DTOs, mapper, exception handler
│   └── out                   persistence: adapters, JPA entities, repository, mapper
└── config                    wires use cases to adapters; OpenAPI info
```

Dependency rule: `adapters` and `config` depend on `ports` and `core`; `core` depends on nothing outside itself.

## Endpoints

| Method | Path                    | Success | Errors   |
|--------|-------------------------|---------|----------|
| POST   | `/api/v1/patients`      | 201 + `Location` header and body | 400 |
| GET    | `/api/v1/patients/{id}` | 200     | 404      |
| PUT    | `/api/v1/patients/{id}` | 204     | 400, 404 |
| DELETE | `/api/v1/patients/{id}` | 204     | 404      |

Example body:

```json
{
  "name": "Maria Silva",
  "address": { "street": "Rua da Aurora, 100", "city": "Recife", "state": "PE" }
}
```
