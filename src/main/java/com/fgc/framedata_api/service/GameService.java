package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.model.GameDTO;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class GameService implements GameServiceInterface {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDTO addGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setName(gameDTO.getName());
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

    public GameDTO updateGame(Long id, GameDTO gameDTO) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.GameNotFoundException("Game not found with id: " + id));

        if (gameDTO.getName() != null && !gameDTO.getName().isEmpty()) {
            existingGame.setName(gameDTO.getName());
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
