README och portfolio-underlag – Mini API
README.md för GitHub
Mini API
Vad projektet gör
Det här är ett kundhanterings-API byggt i Java och Spring Boot.  
Man kan läsa och administrera kunder via API-endpoints.  
Projektet visar backendutveckling med databas, säkerhet, dokumentation och tester.

En backend-applikation byggd med Java, Spring Boot och PostgreSQL för att hantera kunder via ett REST API.
Projektet är byggt som ett portfolio-case för att visa modern backend-utveckling med:
    • Spring Boot
    • REST API
    • PostgreSQL
    • Flyway
    • JWT-autentisering
    • rollbaserad åtkomst
    • Swagger / OpenAPI
    • Docker Compose
    • integrationstester med Testcontainers

Syfte
Målet med projektet är att visa hur man bygger en tydligt strukturerad backend-applikation med:
    • lagerindelad arkitektur
    • databasdriven lagring
    • säkra endpoints
    • API-dokumentation
    • tester mot riktig databas
Det här projektet är inte bara en “hello world”-demo, utan ett litet men realistiskt backend-case.

Teknikstack
    • Java 17
    • Spring Boot
    • Spring Web
    • Spring Data JPA
    • Spring Security
    • PostgreSQL
    • Flyway
    • JWT
    • Swagger / OpenAPI
    • Docker Compose
    • JUnit 5
    • Testcontainers

Funktioner
Publika endpoints
    • Hämta alla kunder
    • Hämta kund via id
    • Sök kund via e-post
    • Kolla API-status
Skyddade endpoints
    • Skapa kund
    • Uppdatera kund
    • Ta bort kund
Autentisering
    • JWT-baserad inloggning
    • rollbaserad åtkomst för admin
Databas
    • PostgreSQL som huvuddatabase
    • Flyway för migreringar och seed-data
Testning
    • integrationstester med riktig PostgreSQL-container via Testcontainers

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
│       └── db/migration/
└── test/
    └── java/se/erica/miniapi/controller/
Kort om mapparna
    • controller: tar emot HTTP-anrop
    • service: affärslogik
    • repository: databasåtkomst
    • dto: request/response-objekt
    • model: entiteter
    • security: JWT och autentisering
    • exception: central felhantering
    • db/migration: Flyway-skript

Arkitektur
Projektet använder en klassisk lagerindelad arkitektur:
Controller → Service → Repository → Database
Det gör koden lättare att förstå, testa och bygga vidare på.

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
  "token": "eyJhbGciOiJIUzI1NiJ9..." 
} 
Customers
Hämta alla kunder
GET /api/customers
Hämta kund via id
GET /api/customers/{id}
Sök kund via e-post
GET /api/customers?email=anna@example.com
Skapa kund
POST /api/customers
Authorization: Bearer <token>
Uppdatera kund
PUT /api/customers/{id}
Authorization: Bearer <token>
Ta bort kund
DELETE /api/customers/{id}
Authorization: Bearer <token>
Status
GET /api/customers/status

Säkerhet
Projektet använder Spring Security tillsammans med JWT.
Flöde
    1. användaren loggar in via /auth/login
    2. API:t returnerar en JWT-token
    3. token skickas med i Authorization-headern
    4. skyddade endpoints kräver giltig token
Exempel på header:
Authorization: Bearer <din-token>
Rollhantering
Projektet använder roller för att styra åtkomst:
    • ROLE_ADMIN
    • ROLE_USER
I nuläget är admin-rollen den som får skapa, uppdatera och ta bort kunder.

Databas och migreringar
Projektet använder PostgreSQL.
Databasschemat och seed-data hanteras med Flyway via migreringsfiler i:
src/main/resources/db/migration/

Swagger / OpenAPI
API-dokumentation finns via Swagger UI.
När appen körs lokalt hittar du dokumentationen här:
http://localhost:8080/swagger-ui/index.html

Köra projektet lokalt
Krav
Du behöver ha installerat:
    • Java 17
    • Maven
    • Docker
1. Starta PostgreSQL med Docker Compose
docker compose up -d 
2. Starta applikationen
mvn spring-boot:run 
3. Öppna Swagger
http://localhost:8080/swagger-ui/index.html

Miljövariabler
Projektet använder miljövariabler för databas och JWT-inställningar.
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION_MS
SERVER_PORT

Tester
Projektet innehåller integrationstester med Testcontainers.
Kör tester
mvn test 
Testerna verifierar bland annat
    • att publika GET-endpoints fungerar
    • att skyddade endpoints kräver token
    • att inloggning fungerar
    • att kund kan skapas efter lyckad autentisering

Exempel med curl
Logga in
curl -X POST http://localhost:8080/auth/login \ 
  -H "Content-Type: application/json" \ 
  -d "{\"username\":\"erica\",\"password\":\"hemligt123\"}" 
Hämta alla kunder
curl http://localhost:8080/api/customers 
Skapa kund
curl -X POST http://localhost:8080/api/customers \ 
  -H "Authorization: Bearer DIN_TOKEN_HÄR" \ 
  -H "Content-Type: application/json" \ 
  -d "{\"name\":\"Lisa\",\"email\":\"lisa@example.com\"}" 

Vad projektet visar
Det här projektet visar att jag kan:
    • bygga REST API:er i Java och Spring Boot
    • strukturera kod i tydliga lager
    • arbeta med PostgreSQL och databasmodeller
    • hantera säkerhet med JWT och roller
    • dokumentera API:er med Swagger
    • använda Flyway för migreringar
    • skriva integrationstester med Testcontainers
    • köra lokal utveckling med Docker Compose

Möjliga nästa steg
Projektet kan byggas vidare med till exempel:
    • refresh tokens
    • användarregistrering
    • fler roller och behörighetsnivåer
    • global response wrapper
    • pagination
    • CI/CD
    • deploy till Render, Railway eller Fly.io
    • frontend-klient som konsumerar API:t

Författare
Erica Nordlöf
GitHub / portfolio / kontakt kan läggas här.

Extra portfolio-noter
För att göra repot ännu vassare i GitHub:
    1. Lägg till 2–3 screenshots
        ◦ Swagger UI
        ◦ testkörning i terminal
        ◦ repo-strukturen
    2. Lägg till en kort sektion högst upp i README:
        ◦ vad projektet gör
        ◦ varför du byggde det
        ◦ vad du lärde dig
    3. Lägg till länk till live-demo om du deployar det.
    4. Lägg gärna till en kort rad i din CV eller LinkedIn:
Byggde en Spring Boot-baserad REST API i Java med PostgreSQL, Flyway, JWT-autentisering, rollbaserad åtkomst, OpenAPI/Swagger, Docker Compose och integrationstester med Testcontainers.
