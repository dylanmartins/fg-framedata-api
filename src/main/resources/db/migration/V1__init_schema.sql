CREATE TABLE characters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE moves (
    id SERIAL PRIMARY KEY,
    character_id INTEGER NOT NULL REFERENCES characters(id),
    name VARCHAR(100) NOT NULL,
    startup_frames INT,
    active_frames INT,
    recovery_frames INT,
    frame_advantage INT,
);
