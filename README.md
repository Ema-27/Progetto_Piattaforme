# 🚀PROGETTO_PIATTAFORME🚀

A full-stack e-commerce platform built with modern web technologies.

## 🛠️ Built with

- **Backend**: Java with Spring Boot
- **Frontend**: Angular
- **Database**: MySQL
- **DevOps**: Docker & Docker Compose
- **Build Tools**: Maven, npm

---
## 📄 Table of Contents

- [✨ Overview](#-overview)
- [🚀 Getting Started](#-getting-started)
  - [📋 Prerequisites](#-prerequisites)
  - [⚙️ Quick start (Docker)](#️-quick-start-docker)
  - [💻 Manual start (dev mode)](#-manual-start-dev-mode)
  - [🧩 Environment / Configuration](#-environment--configuration)
- [🛠️ Seeding initial data](#️-seeding-initial-data)
- [⚙️ Development tips](#️-development-tips)
- [🤝 Contributing](#-contributing)
- [📝 License](#-license)
- [📋Contatti](#-contact)
---

## ✨ Overview

**Progetto_Piattaforme** è un progetto full‑stack che mette insieme un backend Spring Boot, un frontend Angular e un database MySQL, orchestrati tramite Docker Compose. L'obiettivo è offrire un ambiente pronto per sviluppo e test di un'applicazione e‑commerce.

### Principali caratteristiche:
- 🐳 Docker Compose per avviare rapidamente stack multi-container
- ⚡ Backend Spring Boot con repository, servizi e controller
- 🎨 Frontend Angular con configurazioni per sviluppo e produzione
- 📊 Script / meccanismi per inserire dati di esempio nel DB
- 🔄 Hot reload per sviluppo rapido
- 🌐 Configurazione CORS ottimizzata

---

## 🚀 Getting Started

### 📋 Prerequisites

Assicurati di avere installati:
- **Docker & Docker Compose** (raccomandato)
- **Java 17** (se vuoi eseguire localmente il backend senza Docker)
- **Maven 3.6** (per build locale backend)
- **Node.js 16 / npm** (per build e sviluppo frontend)

---

### ⚙️ Quick start (Docker)

Il modo più semplice per avviare l'intero progetto è tramite Docker Compose:

1. **Clone the repository:**

    ```sh
    ❯ git clone https://github.com/Ema-27/Progetto_Piattaforme
    ```

2. **Navigate to the project directory:**

    ```sh
    ❯ cd Progetto_Piattaforme
    ```
3. **Avvia tutti i servizi:**
   
  ```sh
  docker compose up --build
  ```

Questo comando:
- 🏗️ costruisce l'immagine del backend (il Dockerfile effettua il `mvn package`)
- 🗄️avvia MySQL, backend e frontend in container separati
- 🔗mappa le porte (es. backend: 8080, frontend: 4200)

Accedi all'applicazione:
- Frontend: http://localhost:4200


Se vuoi eseguire in background:
```bash
docker compose up --build -d
```

Per fermare e rimuovere i container:
```bash
docker compose down
```

Nota: il backend nel compose è configurato per connettersi al servizio `mysql`. Se il backend parte prima che MySQL sia pronto, potresti vedere errori di connessione.

---

### 💻 Manual start (dev mode)

Se preferisci avviare frontend e backend separatamente per sviluppo:

Backend (da `back-end`):
```bash
# build e avvio con maven
cd back-end
mvn clean package -DskipTests
java -jar target/*.jar
```

Frontend (da `front-end`):
```bash
cd front-end
npm install
# per sviluppo con hot reload
npx ng serve --host 0.0.0.0 --port 4200 --proxy-config src/proxy.conf.json
# oppure build per produzione
npm run build
# e servire la build con un webserver semplice (se necessario)
```

Usare il proxy del dev server Angular evita problemi CORS: il dev server proxya le chiamate API al backend interno.

---

## 🧩 Environment / Configuration

Principali variabili che potresti voler modificare (es. in docker-compose o come env vars):
- SPRING_DATASOURCE_URL (es. jdbc:mysql://mysql:3306/progetto?serverTimezone=UTC&useSSL=false)
- SPRING_DATASOURCE_USERNAME (es. root)
- SPRING_DATASOURCE_PASSWORD (es. admin)
- APP_CORS_ALLOWED_ORIGINS (se hai implementato la lettura delle origini dal config)

In ambiente di sviluppo con Docker Compose, queste sono già settate nel file `docker-compose.yml`.

---

## 🛠️ Seeding initial data

Per avere dati di esempio all'avvio hai due opzioni integrate nel progetto:

1. DataSeeder (Java) — raccomandato:
   - classe `DataSeeder` che esegue insert solo se i record non esistono.
   - funziona con `spring.jpa.hibernate.ddl-auto=update`.
   - migliore affidabilità rispetto a `data.sql`.

2. Utilizzo di strumenti API
  - Postman, curl, o altri client REST
  - Inserimento manuale tramite endpoint
    
Esempio di come verificare i dati:
- GET /categories
- GET /products
- oppure controllo diretto in MySQL (`docker compose exec mysql mysql -u root -p`)

---

## ⚙️ Development tips

- In Codespaces / ambienti remoti: bindare i server su `0.0.0.0` e usare proxy del dev server per evitare CORS.
- CORS: per sviluppo usa `allowedOriginPatterns(["*"])` o configura `app.cors.allowed-origins` con gli URL di preview; in produzione definisci origini esplicite.
- Per ri-buildare il backend quando modifichi il codice e stai usando Docker con multi-stage build, esegui:
  ```bash
  docker compose build backend
  docker compose up --build
  ```

---

## 🤝 Contributing

Se vuoi contribuire:
1. Fork del repository
2. Crea un branch feature/bugfix
3. Aggiungi test se appropriato
4. Apri una PR descrivendo il cambiamento
5. Mantieni i commit atomici e usa message in imperative mood (es. `feat(backend): add sample data seeder`)

---

### Contact
- Author: Ema-27
- Repository: https://github.com/Ema-27/Progetto_Piattaforme

---

## 📝 License

This project is released under the MIT license. See the `LICENSE` file for details.

---
