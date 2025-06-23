package com.fgc.framedata_api.service;

import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.request.CreateCharacterRequest;
import com.fgc.framedata_api.request.UpdateCharacterRequest;

import java.util.List;
import java.util.Optional;

public interface CharacterServiceInterface {
    CharacterDTO addCharacter(CreateCharacterRequest createCharacterRequest);
    List<CharacterDTO> getAllCharacters();
    CharacterDTO getCharacterById(Long id);
    CharacterDTO updateCharacter(Long id, UpdateCharacterRequest updateCharacterRequest);
    void deleteCharacter(Long id);
}
