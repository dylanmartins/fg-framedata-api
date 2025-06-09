package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.dto.GameDTO;
import com.fgc.framedata_api.repository.CharacterRepository;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.request.CreateCharacterRequest;
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

    public Optional<CharacterDTO> getCharacterById(Long id) {
        return characterRepository.findById(id).map(this::mapToDTO);
    }

    public Optional<CharacterDTO> updateCharacter(Long id, CharacterDTO characterDTO) {
        return characterRepository.findById(id).map(existingGame -> {
            if (characterDTO.getName() != null && !characterDTO.getName().isEmpty()) {
                existingGame.setName(characterDTO.getName());
            }
            Character updated = characterRepository.save(existingGame);
            return mapToDTO(updated);
        });
    }

    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }

    private CharacterDTO mapToDTO(Character character) {
        CharacterDTO dto = new CharacterDTO();
        GameDTO gameDTO = new GameDTO(
                character.getGame().getId(),
                character.getGame().getName(),
                null,
                character.getGame().getCreatedAt(),
                character.getGame().getUpdatedAt()
        );
        dto.setName(character.getName());
        dto.setGame(gameDTO);
        return dto;
    }
}