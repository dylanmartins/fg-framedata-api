package com.fgc.framedata_api.dto;

public class CharacterDTO {

    private String name;
    private String gameName;
    private GameDTO game;

    public CharacterDTO() {
    }

    public CharacterDTO(String name) {
        this.name = name;
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
}
