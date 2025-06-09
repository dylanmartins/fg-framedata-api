package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.dto.GameDTO;
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

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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
        return new GameDTO(
                game.getId(),
                game.getName(),
                new ArrayList<>(),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }
}
