# Full Stack Reports

A monorepo fullstack application for managing manufacturing car reports.

- **Backend**: Kotlin / Spring Boot 3 / MongoDB
- **Frontend**: React 19 / Vite / ag-grid / Shadcn/ui
- **API Bridge**: OpenAPI spec auto-generated from BE annotations → TypeScript client/types for FE

## Prerequisites

You need these installed on your machine:

| Tool | Check | Install |
|------|-------|---------|
| **Docker** | `docker --version` | [docs.docker.com/engine/install](https://docs.docker.com/engine/install/) |
| **Docker Compose** | `docker compose version` | Included with Docker Desktop; on Linux: `apt install docker-compose-v2` |
| **Java 21** | `java -version` | `sudo apt install openjdk-21-jdk` (Ubuntu) or [sdkman.io](https://sdkman.io/) |
| **Node.js 22+** | `node --version` | [nodejs.org](https://nodejs.org/) or `nvm install 22` |

> **macOS users**: If you use [Colima](https://github.com/abiosoft/colima) instead of Docker Desktop, start it with `colima start --cpu 4 --memory 8` before running any `docker` commands.

---

## Getting Started (first time)

From the project root (`fullstackreports/`), run these steps in order:

### 1. Install frontend dependencies

```bash
cd frontend
npm install
cd ..
```

### 2. Start everything with Docker

```bash
make up
```

This builds and starts **3 containers**:
- **MongoDB** on port 27017 — the database
- **Backend** on port 8080 — Spring Boot API server
- **Frontend** on port 5173 — Vite dev server with hot reload

### 3. Wait for services to be ready

```bash
# Watch the logs until you see the backend has started
make logs
```

Look for `Started FullStackReportsApplication` in the backend logs. Press `Ctrl+C` to exit the log viewer.

### 4. Open the app

- **App**: http://localhost:5173 — the ag-grid table with 10 seeded reports
- **Swagger UI**: http://localhost:8080/swagger-ui.html — interactive API docs
- **API directly**: http://localhost:8080/api/reports — raw JSON

---

## Running Without Docker (local development)

If you prefer running services directly on your machine (faster iteration, easier debugging):

### Terminal 1 — Start MongoDB only

```bash
docker compose -f docker/docker-compose.yml up mongodb -d
```

### Terminal 2 — Start the backend

```bash
cd backend
./gradlew bootRun
```

Wait until you see `Started FullStackReportsApplication`. The API is now at http://localhost:8080.

### Terminal 3 — Start the frontend

```bash
cd frontend
npm run dev
```

Open http://localhost:5173. The Vite proxy forwards `/api/*` requests to the backend automatically.

---

## Daily Workflow

```bash
# Start all services
make up

# Stop all services
make down

# View live logs
make logs

# After changing backend API endpoints/DTOs, regenerate TypeScript types:
make api-sync

# Restart backend (re-seeds sample data if DB is empty)
make seed

# Nuclear option: remove containers, volumes, and generated files
make clean
```

---

## Services

| Service  | URL                              | Description                |
|----------|----------------------------------|----------------------------|
| Frontend | http://localhost:5173            | React SPA (Vite dev server)|
| Backend  | http://localhost:8080            | Spring Boot REST API       |
| Swagger  | http://localhost:8080/swagger-ui.html | Interactive API docs  |
| MongoDB  | localhost:27017                  | Database                   |

## How the OpenAPI Bridge Works

1. The backend uses **springdoc-openapi** annotations on its DTOs and controllers
2. When the backend is running, it exposes the OpenAPI spec at `/v3/api-docs`
3. `make api-sync` (or `npm run api:sync` in the frontend) fetches that spec and runs **openapi-generator** to produce TypeScript interfaces + a fetch-based API client in `frontend/src/api/generated/`
4. The frontend imports these generated types for type-safe API calls

## Project Structure

```
fullstackreports/
├── backend/                  # Kotlin Spring Boot app
│   ├── src/main/kotlin/
│   │   └── com/fullstackreports/
│   │       ├── model/            # Report entity (MongoDB document)
│   │       ├── repository/       # Data access (MongoRepository)
│   │       ├── service/          # Business logic (CRUD)
│   │       ├── controller/       # REST endpoints + OpenAPI annotations
│   │       ├── dto/              # Request/Response DTOs
│   │       └── config/           # CORS, seed data
│   ├── src/main/resources/
│   │   └── application.yml       # DB connection, springdoc config
│   └── build.gradle.kts          # Dependencies & build config
├── frontend/                 # React Vite SPA
│   ├── src/
│   │   ├── api/              # API service + React Query hooks
│   │   ├── pages/            # ReportListPage, ReportDetailPage
│   │   └── components/ui/    # Shadcn/ui components
│   ├── package.json
│   └── vite.config.ts        # Proxy config + Tailwind
├── docker/
│   ├── docker-compose.yml    # All 3 services defined here
│   ├── backend.Dockerfile    # Multi-stage: Gradle build → JRE runtime
│   └── frontend.Dockerfile   # Node dev server with HMR
├── Makefile                  # Convenience commands
└── README.md
```
