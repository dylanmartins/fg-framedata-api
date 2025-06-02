package com.fgc.framedata_api.model;

public class GameDTO {

    private Long id;
    private String name;
    private java.util.List<CharacterDTO> characters;

    public GameDTO() {
    }

    public GameDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<CharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(java.util.List<CharacterDTO> characters) {
        this.characters = characters;
    }
}
