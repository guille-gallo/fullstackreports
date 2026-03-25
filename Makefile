.PHONY: up down logs api-sync seed clean build-backend build-frontend

COMPOSE := docker compose -f docker/docker-compose.yml

## Start all services
up:
	$(COMPOSE) up -d --build

## Stop all services
down:
	$(COMPOSE) down

## Tail logs from all services
logs:
	$(COMPOSE) logs -f

## Fetch OpenAPI spec from BE and regenerate FE TypeScript types
api-sync:
	curl -s http://localhost:8080/v3/api-docs -o frontend/src/api/openapi.json
	cd frontend && npx @openapitools/openapi-generator-cli generate \
		-i src/api/openapi.json \
		-g typescript-fetch \
		-o src/api/generated \
		--additional-properties=supportsES6=true,typescriptThreePlus=true

## Restart backend to re-run seed data
seed:
	$(COMPOSE) restart backend

## Remove containers, volumes, and generated files
clean:
	$(COMPOSE) down -v --remove-orphans
	rm -rf frontend/src/api/generated
	rm -f frontend/src/api/openapi.json

## Build backend locally (no Docker)
build-backend:
	cd backend && ./gradlew bootJar

## Build frontend locally (no Docker)
build-frontend:
	cd frontend && npm run build
