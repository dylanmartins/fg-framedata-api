# Service State & Roadmap

## Current State

Spring Boot 3.5 / Java 21 REST API backed by PostgreSQL (Docker) and H2 (tests).

**What exists:**
- `Game` and `Character` entities with full CRUD (controllers, services, repositories, DTOs)
- Layered architecture: controller → service (interface-backed) → repository → JPA entity
- Docker Compose setup for local development
- Integration and unit test suites

**What's missing:**
- `Move` entity — the core data of the service doesn't exist yet
- No markdown ingestion pipeline
- No seed data

---

## Data Model Target

### `patch_version` on `Character`
Represents the SF6 patch this character's frame data was last updated against (e.g. `"20250222"`). When a new markdown file is ingested, all moves for that character are replaced and `patch_version` is updated.

### `Move` entity (SF6-scoped)

| Field | Type | Notes |
|---|---|---|
| `id` | Long | PK |
| `character_id` | Long | FK → Character |
| `name` | String | e.g. "Hadoken" |
| `input` | String | e.g. "236P" |
| `move_type` | Enum | NORMAL, SPECIAL, SUPER_ART, THROW, OD_SPECIAL, STANCE, TARGET_COMBO |
| `super_art_level` | Integer (nullable) | 1, 2, or 3 — null if not a SA |
| `damage` | Integer | |
| `chip_damage` | Integer (nullable) | Damage on block for specials/SAs |
| `startup_frames` | Integer | |
| `active_frames` | Integer | |
| `recovery_frames` | Integer | |
| `on_hit_advantage` | Integer | |
| `on_block_advantage` | Integer | |
| `on_counter_hit_advantage` | Integer | |
| `on_punish_counter_advantage` | Integer | |
| `drive_rush_on_hit` | Integer (nullable) | Advantage after DR cancel on hit |
| `drive_rush_on_block` | Integer (nullable) | Advantage after DR cancel on block |
| `has_armor` | Boolean | |
| `armor_hits` | Integer (nullable) | How many hits of armor |
| `cancel_options` | String | e.g. "Special, SA1, DR" |
| `parent_move_id` | Long (nullable) | FK → Move (self-referential). Set on follow-ups, target combo continuations, and stance follow-ups — points to the preceding/enabling move. Null for standalone moves. |
| `notes` | String (nullable) | Anything that doesn't fit a column |

---

## Roadmap (JIRA-style)

---

### Epic 1 — Move Data Model

**[MODEL-1] Add `patch_version` to `Character` entity**
Add a `patch_version` String field to the `Character` JPA entity and update the existing DTOs/requests to include it.

**[MODEL-2] Implement `Move` entity and repository**
Create the `Move` JPA entity with all SF6 fields listed above, mapped as a `@ManyToOne` to `Character`. Add a nullable `@ManyToOne(self) parent_move_id` for follow-ups, target combos, and stance follow-ups — children point to their preceding/enabling move. Create `MoveRepository` with `findAllByCharacterId(Long id)` and `findAllByParentMoveId(Long id)` methods.

---

### Epic 2 — Move API

**[API-1] Move DTOs, request/response models**
Create `MoveDTO`, `CreateMoveRequest`, `UpdateMoveRequest`, and `ListMoveResponse` following the existing patterns in `dto/`, `request/`, and `response/`.

**[API-2] `MoveService` and `MoveController`**
Implement `MoveServiceInterface` and `MoveService` with full CRUD. Expose via `MoveController` under `/games/{gameId}/characters/{characterId}/moves`. These endpoints exist primarily for testing and manual data entry — the main write path will be markdown ingestion.

**[API-3] Move tests**
Unit tests (Mockito) for `MoveService` and integration tests (MockMvc + H2) for `MoveController`, following the existing test structure.

---

### Epic 3 — Markdown Format & Parser

**[MD-1] Define and document the markdown schema**
Design the `.md` format for a character file. One file per character, living under `data/sf6/{character-name}.md`. Example structure:

```markdown
---
game: Street Fighter 6
character: Ryu
patch_version: "20250222"
---

## Moves

| Name | Input | Type | Parent | Damage | Chip | Startup | Active | Recovery | On Hit | On Block | On Counter Hit | On Punish Counter | DR On Hit | DR On Block | Armor | Armor Hits | Cancel Options | Notes |
|------|-------|------|--------|--------|------|---------|--------|----------|--------|----------|----------------|-------------------|-----------|-------------|-------|------------|----------------|-------|
| Standing Light Punch | 5LP | NORMAL | - | 300 | - | 4 | 3 | 9 | +4 | +2 | +6 | +8 | +6 | +4 | false | - | Special, SA1, SA2, SA3, DR | |
| Slash Elbow | 41236P | STANCE | - | 800 | - | 16 | 3 | 22 | +2 | -4 | +4 | +8 | - | - | false | - | - | Enters Power Bomb stance |
| Power Bomb Follow-up A | P | TARGET_COMBO | Slash Elbow | 1200 | - | 8 | 2 | 18 | +0 | -6 | +2 | +6 | - | - | false | - | - | |
```

The schema itself should be documented in `docs/markdown-schema.md`.

**[MD-2] Implement markdown parser**
A Spring `@Service` that reads a character `.md` file and maps its contents to `CreateMoveRequest` objects plus character metadata (`game`, `character`, `patch_version`). Use a YAML front-matter parser for the header block and a table parser for the moves section.

**[MD-3] Implement ingestion service**
A service that, given a parsed character file:
1. Finds or creates the `Game` record by name
2. Finds or creates the `Character` record by name + game
3. Deletes all existing `Move` records for that character
4. Inserts all parsed moves
5. Updates `patch_version` on the character

Expose a protected endpoint `POST /admin/ingest?file=data/sf6/ryu.md` for manual triggering during development and CI use.

---

### Epic 4 — Ryu Seed Data

**[SEED-1] One-time import from community source**
Write a standalone script (can be a `@SpringBootTest` or a simple `main()`) that fetches Ryu's frame data from [FAT (Frame Assistant Tool)](https://github.com/D4RKONION/FAT) — which stores data as JSON — and generates `data/sf6/ryu.md` in the format defined in MD-1. Run once, commit the resulting file, then discard the script.

**[SEED-2] Validate Ryu through the ingestion pipeline**
Run `ryu.md` through the MD-3 ingestion endpoint end-to-end and verify the DB state matches the source data. This also serves as a live test of the full pipeline before CI is wired up.

---

### Epic 5 — CI Ingestion Pipeline

**[CI-1] GitHub Action: detect changed markdown files on merge**
On push to `main`, a workflow step diffs the changed files and collects any `.md` paths under `data/sf6/`.

**[CI-2] Trigger ingestion for changed files**
The workflow calls `POST /admin/ingest?file={path}` for each changed file against the deployed service. Requires the deploy target to be decided (see below).

**[CI-3] Manual workflow dispatch**
Add a `workflow_dispatch` trigger to the same action so any file can be re-ingested manually from the GitHub UI without a code change.

---

### Epic 6 — Query Capabilities

**[QUERY-1] Filter moves endpoint**
Add query params to `GET /games/{gameId}/characters/{characterId}/moves`:
- `?type=NORMAL` — filter by move type
- `?minOnBlock=0` — filter moves that are neutral or better on block
- `?minOnHit=0` — same for on hit

This is the foundation for what the Obsidian-like service will query most often.

**[QUERY-2] Character list by game**
`GET /games/{gameId}/characters` already exists structurally — verify it returns `patch_version` in the response so consumers know how fresh the data is.

---

## Open Decisions

| Decision | Status |
|---|---|
| Deployment target (Railway, Fly.io, GCP, etc.) | TBD — needed before Epic 5 |
| Whether `data/sf6/` markdown files live in this repo long-term or move to a separate community repo | TBD — start here, migrate later if needed |
| Expanding beyond SF6 (generic `properties` JSON column approach) | Deferred — revisit when a second game is needed |
