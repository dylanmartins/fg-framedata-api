package com.fgc.framedata_api.request;

import com.fgc.framedata_api.dto.CharacterDTO;

import java.util.List;

public class UpdateGameRequest {
    private String name;
    private List<CharacterDTO> characters;

    public UpdateGameRequest() {
    }

    public UpdateGameRequest(String name, List<CharacterDTO> characters) {
        this.name = name;
        this.characters = characters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDTO> characters) {
        this.characters = characters;
    }
}
