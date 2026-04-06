DROP TABLE moves;

CREATE TABLE moves (
    id                          BIGSERIAL PRIMARY KEY,
    character_id                BIGINT NOT NULL REFERENCES characters(id),
    parent_move_id              BIGINT REFERENCES moves(id),
    name                        VARCHAR(100) NOT NULL,
    input                       VARCHAR(50) NOT NULL,
    move_type                   VARCHAR(50) NOT NULL,
    super_art_level             INTEGER,
    damage                      INTEGER,
    chip_damage                 INTEGER,
    startup_frames              INTEGER,
    active_frames               INTEGER,
    recovery_frames             INTEGER,
    on_hit_advantage            INTEGER,
    on_block_advantage          INTEGER,
    on_counter_hit_advantage    INTEGER,
    on_punish_counter_advantage INTEGER,
    drive_rush_on_hit           INTEGER,
    drive_rush_on_block         INTEGER,
    has_armor                   BOOLEAN NOT NULL DEFAULT FALSE,
    armor_hits                  INTEGER,
    cancel_options              VARCHAR(200),
    notes                       TEXT
);
