package com.fgc.framedata_api.service;

import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.request.CreateCharacterRequest;

import java.util.List;
import java.util.Optional;

public interface CharacterServiceInterface {
    CharacterDTO addCharacter(CreateCharacterRequest createCharacterRequest);
    List<CharacterDTO> getAllCharacters();
    Optional<CharacterDTO> getCharacterById(Long id);
    Optional<CharacterDTO> updateCharacter(Long id, CharacterDTO characterDTO);
    void deleteCharacter(Long id);
}
