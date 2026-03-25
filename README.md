# Full Stack Reports

A monorepo fullstack application for managing manufacturing car reports.

- **Backend**: Kotlin / Spring Boot 3 / MongoDB
- **Frontend**: React 19 / Vite / ag-grid / Shadcn/ui
- **API Bridge**: OpenAPI spec auto-generated from BE annotations → TypeScript client/types for FE

## Prerequisites

- [Colima](https://github.com/abiosoft/colima) (or Docker Desktop)
- Docker & Docker Compose
- Java 21 (for local BE development without Docker)
- Node.js 22+ (for local FE development without Docker)

## Quick Start

```bash
# Start Colima (macOS)
colima start --cpu 4 --memory 8

# Start all services (MongoDB, Backend, Frontend)
make up

# View logs
make logs

# Stop everything
make down
```

## Services

| Service  | URL                              | Description                |
|----------|----------------------------------|----------------------------|
| Frontend | http://localhost:5173            | React SPA (Vite dev server)|
| Backend  | http://localhost:8080            | Spring Boot REST API       |
| Swagger  | http://localhost:8080/swagger-ui.html | Interactive API docs  |
| MongoDB  | localhost:27017                  | Database                   |

## Development

### Sync OpenAPI types (after BE changes)

```bash
make api-sync
```

This fetches the OpenAPI spec from the running backend and regenerates TypeScript types/client in `frontend/src/api/generated/`.

### Project Structure

```
fullstackreports/
├── backend/                  # Kotlin Spring Boot app
│   ├── src/main/kotlin/...   # Source code
│   ├── src/main/resources/   # application.yml
│   └── build.gradle.kts      # Gradle build config
├── frontend/                 # React Vite SPA
│   ├── src/
│   │   ├── api/              # Generated API client + hooks
│   │   ├── pages/            # Route pages
│   │   └── components/       # Shadcn/ui components
│   ├── package.json
│   └── vite.config.ts
├── docker/                   # Docker configs
│   ├── docker-compose.yml
│   ├── backend.Dockerfile
│   └── frontend.Dockerfile
├── Makefile                  # DX convenience commands
└── README.md
```
