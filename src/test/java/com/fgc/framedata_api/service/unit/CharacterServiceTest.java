package com.fgc.framedata_api.service.unit;

import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.repository.CharacterRepository;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.request.CreateCharacterRequest;
import com.fgc.framedata_api.request.UpdateCharacterRequest;
import com.fgc.framedata_api.service.CharacterService;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private CharacterService characterService;

    @Test
    void updateCharacter_shouldReturnNotFound_whenCharacterNotFound() {
        Long characterId = 1L;
        UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest();
        updateCharacterRequest.setName("Updated Character");

        // Simulate the scenario where the character is not found
        when(characterRepository.findById(characterId)).thenReturn(Optional.empty());

        try {
            characterService.updateCharacter(characterId, updateCharacterRequest);
        } catch (CustomExceptions.CharacterNotFoundException e) {
            assert e.getMessage().equals("Character not found with id: " + characterId);
        }
    }

    @Test
    void updateCharacter_shouldReturnCharacterDTO_whenRequestIsValid() {
        Long characterId = 1L;
        UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest();
        updateCharacterRequest.setName("Updated Character");

        // Set up a character that will be returned by the service
        Character existingCharacter = new Character();
        existingCharacter.setId(characterId);
        existingCharacter.setName("Old Character");
        Game game = new Game();
        game.setId(1L);
        game.setName("Test Game");
        existingCharacter.setGame(game);

        // Mock the repository calls
        when(characterRepository.findById(characterId)).thenReturn(Optional.of(existingCharacter));
        when(characterRepository.save(any(Character.class))).thenReturn(existingCharacter);

        CharacterDTO updatedCharacter = characterService.updateCharacter(characterId, updateCharacterRequest);
        assert updatedCharacter != null;
        assert updatedCharacter.getName().equals("Updated Character");
    }

    @Test
    void addCharacter_shouldReturnNotFound_whenGameNotFound() {
        CreateCharacterRequest createCharacterRequest = new CreateCharacterRequest();
        createCharacterRequest.setName("Test Character");
        createCharacterRequest.setGameId(1L);

        // Simulate the scenario where the game is not found
        when(gameRepository.findById(createCharacterRequest.getGameId())).thenReturn(Optional.empty());

        try {
            characterService.addCharacter(createCharacterRequest);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + createCharacterRequest.getGameId());
        }
    }

    @Test
    void addCharacter_shouldReturnCharacterDTO_whenRequestIsValid() {
        // Set up a valid game and character
        Game game = new Game();
        game.setId(1L);
        game.setName("Test Game");

        // Create a character that will be returned by the service
        Character character = new Character();
        character.setId(1L);
        character.setName("Test Character");
        character.setGame(game);

        CreateCharacterRequest createCharacterRequest = new CreateCharacterRequest();
        createCharacterRequest.setName("Test Character");
        createCharacterRequest.setGameId(game.getId());

        // Mock the repository calls
        when(gameRepository.findById(createCharacterRequest.getGameId())).thenReturn(Optional.of(game));
        when(characterRepository.save(any(Character.class))).thenReturn(character);

        CharacterDTO characterDTO = characterService.addCharacter(createCharacterRequest);
        assert characterDTO != null;
        assert characterDTO.getName().equals("Test Character");
        assert characterDTO.getGame().getId().equals(game.getId());
    };

    @Test
    void getAllCharacters_shouldReturnEmptyList_whenNoCharactersExist() {
        // Simulate the scenario where no characters exist
        when(characterRepository.findAll()).thenReturn(new ArrayList<>());

        List<CharacterDTO> characters = characterService.getAllCharacters();
        assert characters.isEmpty();
    }

    @Test
    void getAllCharacters_shouldReturnListOfCharacters_whenCharactersExist() {
        Game game = new Game();
        game.setId(1L);
        game.setName("Test Game");

        Character character = new Character();
        character.setId(1L);
        character.setName("Test Character");
        character.setGame(game);

        Character secondCharacter = new Character();
        secondCharacter.setId(2L);
        secondCharacter.setName("Test Character 2");
        secondCharacter.setGame(game);


        List<Character> mockCharacters = new ArrayList<>();
        mockCharacters.add(character);
        mockCharacters.add(secondCharacter);

        when(characterRepository.findAll()).thenReturn(mockCharacters);

        List<CharacterDTO> characters = characterService.getAllCharacters();
        assert !characters.isEmpty();
        assert characters.size() == 2;
    }
}
