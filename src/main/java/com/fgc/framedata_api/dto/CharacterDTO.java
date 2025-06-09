package com.fgc.framedata_api.dto;

public class CharacterDTO {

    private Long id;
    private String name;
    private String gameName;
    private GameDTO game;

    public CharacterDTO() {
    }

    public CharacterDTO(Long id, String name, GameDTO game) {
        this.id = id;
        this.name = name;
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public String getGameName() {
        return game != null ? game.getName() : null;
    }
}
