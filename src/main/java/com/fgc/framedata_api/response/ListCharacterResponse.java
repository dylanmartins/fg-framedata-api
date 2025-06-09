package com.fgc.framedata_api.response;

import com.fgc.framedata_api.dto.CharacterDTO;

import java.util.List;

public class ListCharacterResponse {

    private List<CharacterDTO> characters;

    public ListCharacterResponse() {
    }

    public ListCharacterResponse(List<CharacterDTO> characters) {
        this.characters = characters;
    }

    public List<CharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDTO> characters) {
        this.characters = characters;
    }
}
