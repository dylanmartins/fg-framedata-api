package com.fgc.framedata_api.model;

public class GameDTO {

    private String name;
    private java.util.List<Character> characters;

    public GameDTO() {
    }

    public GameDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(java.util.List<Character> characters) {
        this.characters = characters;
    }
}
