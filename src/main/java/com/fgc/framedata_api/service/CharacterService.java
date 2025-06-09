package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.dto.GameDTO;
import com.fgc.framedata_api.repository.CharacterRepository;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.request.CreateCharacterRequest;
import com.fgc.framedata_api.request.UpdateCharacterRequest;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService implements CharacterServiceInterface {

    private final CharacterRepository characterRepository;
    private final GameRepository gameRepository;

    public CharacterService(CharacterRepository characterRepository, GameRepository gameRepository) {
        this.characterRepository = characterRepository;
        this.gameRepository = gameRepository;
    }

    public CharacterDTO addCharacter(CreateCharacterRequest createCharacterRequest) {
        Long gameId = createCharacterRequest.getGameId();
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomExceptions.GameNotFoundException("Game not found with id: " + gameId));

        Character character = new Character();
        character.setGame(game);
        character.setName(createCharacterRequest.getName());
        character = characterRepository.save(character);
        return mapToDTO(character);
    }

    public List<CharacterDTO> getAllCharacters() {
        return characterRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CharacterDTO getCharacterById(Long id) {
        Character existingCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.CharacterNotFoundException("Character not found with id: " + id));
        return mapToDTO(existingCharacter);
    }

    public CharacterDTO updateCharacter(Long id, UpdateCharacterRequest updateCharacterRequest) {
        Character existingCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.CharacterNotFoundException("Character not found with id: " + id));

        if (updateCharacterRequest.getName() != null && !updateCharacterRequest.getName().isEmpty()) {
            existingCharacter.setName(updateCharacterRequest.getName());
        }
        Character updatedCharacter = characterRepository.save(existingCharacter);
        return mapToDTO(updatedCharacter);
    }

    public void deleteCharacter(Long id) {
        Character existingCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.CharacterNotFoundException("Character not found with id: " + id));

        gameRepository.deleteById(existingCharacter.getId());
    }

    private CharacterDTO mapToDTO(Character character) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(character.getId());
        dto.setName(character.getName());
        if (character.getGame() != null) {
            dto.setGameName(character.getGame().getName());
        }
        return dto;
    }
}