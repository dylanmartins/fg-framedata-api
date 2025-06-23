package com.fgc.framedata_api.service;

import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.dto.GameDTO;
import com.fgc.framedata_api.repository.CharacterRepository;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.request.CreateGameRequest;
import com.fgc.framedata_api.request.UpdateGameRequest;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService implements GameServiceInterface {

    private final GameRepository gameRepository;
    private final CharacterRepository characterRepository;

    public GameService(GameRepository gameRepository, CharacterRepository characterRepository) {
        this.gameRepository = gameRepository;
        this.characterRepository = characterRepository;
    }

    public GameDTO addGame(CreateGameRequest createRequest) {
        Game game = new Game();
        game.setName(createRequest.getName());
        game.setCharacters(new ArrayList<>());
        game = gameRepository.save(game);
        return mapToDTO(game);
    }

    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public GameDTO getGameById(Long id) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.GameNotFoundException("Game not found with id: " + id));
        return mapToDTO(existingGame);
    }

    public GameDTO updateGame(Long id, UpdateGameRequest updateGameRequest) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.GameNotFoundException("Game not found with id: " + id));

        if (updateGameRequest.getName() != null && !updateGameRequest.getName().isEmpty()) {
            existingGame.setName(updateGameRequest.getName());
        }
        if (updateGameRequest.getCharacters() != null) {
            List<Character> characterEntities = updateGameRequest.getCharacters()
                    .stream()
                    .map(dto -> {
                            Character character = new Character();
                            character.setName(dto.getName());
                            character.setGame(existingGame);
                            return character;
                        }
                    )
                    .collect(Collectors.toList());
            existingGame.setCharacters(characterEntities);
        }
        Game updated = gameRepository.save(existingGame);
        return mapToDTO(updated);
    }

    public void deleteGame(Long id) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.GameNotFoundException("Game not found with id: " + id));
        gameRepository.deleteById(existingGame.getId());
    }

    private GameDTO mapToDTO(Game game) {
        List<Character> characters = characterRepository.findAllByGameId(1L);
        if (characters == null || characters.isEmpty()) {
            return new GameDTO(
                    game.getId(),
                    game.getName(),
                    Collections.emptyList(),
                    game.getCreatedAt(),
                    game.getUpdatedAt()
            );
        }

        List<CharacterDTO> characterDTOs = characters.stream()
                .map(character -> new CharacterDTO(
                        character.getId(),
                        character.getName()
                ))
                .collect(Collectors.toList());
        return new GameDTO(
                game.getId(),
                game.getName(),
                characterDTOs,
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }
}
