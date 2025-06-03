CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Add foreign key constraint to characters table
ALTER TABLE characters
ADD COLUMN game_id INTEGER NOT NULL REFERENCES games(id);
