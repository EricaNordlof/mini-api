# Mini API

Ett kundhanterings-API byggt i **Java**, **Spring Boot** och **PostgreSQL**.

Projektet är byggt som ett portfolio-case för att visa modern backendutveckling med:

- Spring Boot
- REST API
- PostgreSQL
- Flyway
- autentisering
- Swagger / OpenAPI
- Docker Compose
- integrationstester med Testcontainers
- deploy till Render

Det här projektet är inte bara en enkel CRUD-demo, utan ett backend-case med databas, säkerhet, dokumentation, lokal utvecklingsmiljö och live-deploy.

---

# Live-demo

**Live API:**  
`https://mini-api-sp7z.onrender.com`

**Swagger UI:**  
`https://mini-api-sp7z.onrender.com/swagger-ui/index.html`

**OpenAPI docs:**  
`https://mini-api-sp7z.onrender.com/v3/api-docs`

---

# Vad projektet gör

API:t hanterar kunder via flera endpoints. Det går att:

- logga in via `/auth/login`
- hämta alla kunder
- söka kund via e-post
- hämta kund via id
- skapa kund
- stoppa dubletter med korrekt HTTP-statuskod (**409 Conflict**)

---

# Live-verifierat

Det här är verifierat i live-miljö på Render:

- `POST /auth/login` fungerar och returnerar token
- `GET /api/customers?email=...` fungerar
- `GET /api/customers/{id}` fungerar
- `GET /api/customers/status` fungerar
- `POST /api/customers` fungerar för ny kund
- nytt försök med samma e-post returnerar **409 Conflict**
- PostgreSQL på Render fungerar
- Flyway kör migreringar korrekt vid deploy
- Swagger UI laddar korrekt i live-miljö

---

# Verifierat lokalt

Det här är verifierat i lokal körning:

- Docker + PostgreSQL startar
- Spring Boot-applikationen startar
- Flyway kör migreringar
- Swagger UI laddar korrekt
- `POST /auth/login` fungerar
- `GET /api/customers` fungerar
- `GET /api/customers?email=...` fungerar
- `GET /api/customers/{id}` fungerar
- `GET /api/customers/status` fungerar
- `POST /api/customers` fungerar för ny kund
- nytt försök med samma e-post returnerar **409 Conflict**

---

# Teknikstack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- Flyway
- Swagger / OpenAPI
- Docker Compose
- JUnit 5
- Testcontainers
- Render

---

# Projektstruktur

```text
src/
├── main/
│   ├── java/se/erica/miniapi/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   └── resources/
│       ├── application.properties
│       ├── application-test.properties
│       └── db/migration/
└── test/
    └── java/se/erica/miniapi/controller/
