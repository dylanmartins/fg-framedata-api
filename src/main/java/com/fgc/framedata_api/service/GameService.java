package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.model.GameDTO;
import com.fgc.framedata_api.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

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

    public Optional<GameDTO> getGameById(Long id) {
        return gameRepository.findById(id).map(this::mapToDTO);
    }

    public Optional<GameDTO> updateGame(Long id, GameDTO gameDTO) {
        return gameRepository.findById(id).map(existingGame -> {
            if (gameDTO.getName() != null && !gameDTO.getName().isEmpty()) {
                existingGame.setName(gameDTO.getName());
            }
            Game updated = gameRepository.save(existingGame);
            return mapToDTO(updated);
        });
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    private GameDTO mapToDTO(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setCharacters(Collections.emptyList());
        return dto;
    }
}
