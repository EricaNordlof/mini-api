Mini API
Ett kundhanterings-API byggt i Java, Spring Boot och PostgreSQL.
Projektet är byggt som ett portfolio-case för att visa modern backendutveckling med:
    • Spring Boot
    • REST API
    • PostgreSQL
    • Flyway
    • autentisering
    • Swagger / OpenAPI
    • Docker Compose
    • integrationstester med Testcontainers
Vad projektet gör
API:t hanterar kunder via flera endpoints. Det går att:
    • logga in via /auth/login
    • hämta alla kunder
    • söka kund via e-post
    • hämta kund via id
    • skapa kund
    • stoppa dubletter med korrekt HTTP-statuskod (409 Conflict)
Det här projektet är inte bara en enkel CRUD-demo, utan ett backend-case med databas, säkerhet, dokumentation och lokal utvecklingsmiljö.
Verifierat lokalt
Det här är verifierat i lokal körning:
    • Docker + PostgreSQL startar
    • Spring Boot-applikationen startar
    • Flyway kör migreringar
    • Swagger UI laddar korrekt
    • POST /auth/login fungerar
    • GET /api/customers fungerar
    • GET /api/customers?email=... fungerar
    • GET /api/customers/{id} fungerar
    • GET /api/customers/status fungerar
    • POST /api/customers fungerar för ny kund
    • nytt försök med samma e-post returnerar 409 Conflict
Teknikstack
    • Java 17
    • Spring Boot
    • Spring Web
    • Spring Data JPA
    • Spring Security
    • PostgreSQL
    • Flyway
    • Swagger / OpenAPI
    • Docker Compose
    • JUnit 5
    • Testcontainers
Projektstruktur
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
Kort om mapparna
    • controller – tar emot HTTP-anrop
    • service – affärslogik
    • repository – databasåtkomst
    • dto – request/response-objekt
    • model – entiteter
    • security – autentisering och säkerhet
    • exception – central felhantering
    • db/migration – Flyway-skript
API-endpoints
Auth
Logga in
POST /auth/login
Request:
{ 
  "username": "erica", 
  "password": "hemligt123" 
} 
Svar:
{ 
  "token": "eyJhbGciOi..." 
} 
Customers
    • GET /api/customers
    • GET /api/customers?email=lisa1@example.com
    • GET /api/customers/{id}
    • POST /api/customers
    • GET /api/customers/status
Exempel på verifierat kundflöde
    1. logga in via /auth/login
    2. skapa kund via POST /api/customers
    3. hämta kund via GET /api/customers?email=lisa1@example.com
    4. hämta kund via GET /api/customers/{id}
    5. testa samma e-post igen och få 409 Conflict
Säkerhet
Projektet använder Spring Security och autentisering i login-flödet.
Nuvarande verifierade läge:
    • /auth/login returnerar token
    • skyddade anrop fungerar i lokal demo
    • säkerhetsdelen kan förfinas ytterligare om målet är ett helt produktionstroget JWT-upplägg för alla skyddade endpoints
Swagger / OpenAPI
När appen körs lokalt finns API-dokumentationen här:
http://localhost:8080/swagger-ui/index.html
Köra projektet lokalt
Krav
Du behöver ha installerat:
    • Java 17
    • Maven
    • Docker
1. Starta databasen
docker compose up -d 
2. Starta applikationen
mvn spring-boot:run 
3. Öppna Swagger
http://localhost:8080/swagger-ui/index.html
Vid lokal felsökning
mvn compile 
mvn spring-boot:run -Dmaven.test.skip=true 
Miljövariabler
Projektet använder följande miljövariabler:
    • DB_URL
    • DB_USERNAME
    • DB_PASSWORD
    • JWT_SECRET
    • JWT_EXPIRATION_MS
    • SERVER_PORT
Det gör projektet enklare att deploya vidare.
Tester
Projektet innehåller integrationstester med Testcontainers.
Kör tester:
mvn test 
Testerna verifierar bland annat:
    • att publika GET-endpoints fungerar
    • att skyddade endpoints kräver autentisering
    • att inloggning fungerar
    • att kund kan skapas efter lyckad autentisering
Vad projektet visar
Det här projektet visar att jag kan:
    • sätta upp lokal backendmiljö med Docker, PostgreSQL och Spring Boot
    • bygga och starta ett REST API lokalt
    • dokumentera endpoints med Swagger
    • arbeta med login-flöde och skyddade anrop
    • hantera kunddata via flera endpoints
    • stoppa dubletter via affärslogik och korrekt HTTP-statuskod
    • strukturera ett backendprojekt på ett sätt som liknar verkliga case
Nästa steg
Möjliga förbättringar:
    • PUT /api/customers/{id}
    • DELETE /api/customers/{id}
    • mer genomkopplat JWT-flöde för alla skyddade endpoints
    • fler integrationstester
    • screenshots i README
    • deploy till Render, Railway eller Fly.io
Författare
Erica Nordlöf
