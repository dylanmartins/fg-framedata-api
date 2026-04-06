# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run full stack (Docker)
make run

# Run tests
make test-local        # equivalent to ./mvnw clean test

# Build
./mvnw clean package

# Start/stop Docker services
make stop
make restart
make clean             # removes containers, image, and volumes
```

Tests run against an in-memory H2 database (profile `test` is activated automatically via the surefire plugin).

## Architecture

Spring Boot 3.5 / Java 21 REST API for fighting game frame data. Uses PostgreSQL in production (Docker service `sf6-db`) and H2 for tests.

**Layered structure** under `src/main/java/com/fgc/framedata_api/`:

| Layer | Package | Purpose |
|---|---|---|
| Entities | `model/` | JPA entities with `@PrePersist`/`@PreUpdate` auditing |
| Repositories | `repository/` | Spring Data JPA interfaces |
| Services | `service/` | Business logic behind interfaces (`*ServiceInterface`) |
| Controllers | `controller/` | REST endpoints, delegate entirely to services |
| DTOs | `dto/`, `request/`, `response/` | API contracts (input requests, output DTOs/list wrappers) |
| Exceptions | `utils/CustomExceptions` | Domain-specific `RuntimeException` subclasses |

**Domain model:** `Game` (1) → (many) `Character`. Both entities have `id`, `name`, and timestamp fields.

**Test layout:**
- `src/test/java/com/fgc/framedata_api/service/unit/` — unit tests with Mockito
- `src/test/java/com/fgc/framedata_api/service/integration/` — `@SpringBootTest` + MockMvc integration tests

## Known Issues

- `GameService.mapToDTO()`: hardcoded `findAllByGameId(1L)` — should use `game.getId()`
- `CharacterService.deleteCharacter()`: calls `gameRepository.deleteById()` instead of `characterRepository.deleteById()`
