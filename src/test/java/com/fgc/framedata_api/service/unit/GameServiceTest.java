package com.fgc.framedata_api.service.unit;

import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.dto.GameDTO;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.request.CreateGameRequest;
import com.fgc.framedata_api.request.UpdateGameRequest;
import com.fgc.framedata_api.utils.CustomExceptions;
import com.fgc.framedata_api.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    void updateGame_shouldReturnEmpty_whenGameNotFound() {
        Long gameId = 1L;
        UpdateGameRequest updateGameRequest = new UpdateGameRequest();
        updateGameRequest.setName("Updated Game Name");

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.updateGame(gameId, updateGameRequest);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + gameId);
        }
    }

    @Test
    void deleteGame_shouldThrowException_whenGameNotFound() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.deleteGame(gameId);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + gameId);
        }
    }

    @Test
    void getGameById_shouldThrowException_whenGameNotFound() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.getGameById(gameId);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + gameId);
        }
    }

    @Test
    void addGame_shouldReturnGameDTO_whenGameIsAdded() {
        CreateGameRequest createRequest = new CreateGameRequest();
        createRequest.setName("Test Game");

        Game game = new Game();
        game.setName(createRequest.getName());
        game.setCreatedAt(LocalDateTime.now());
        game.setUpdatedAt(LocalDateTime.now());

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        GameDTO result = gameService.addGame(createRequest);
        assert result.getName().equals("Test Game");
    }

    @Test
    void getAllGames_shouldReturnListOfGameDTOs() {
        Game game1 = new Game();
        game1.setId(1L);
        game1.setName("Game 1");
        game1.setCreatedAt(LocalDateTime.now());
        game1.setUpdatedAt(LocalDateTime.now());

        Game game2 = new Game();
        game2.setId(2L);
        game2.setName("Game 2");
        game2.setCreatedAt(LocalDateTime.now());
        game2.setUpdatedAt(LocalDateTime.now());

        when(gameRepository.findAll()).thenReturn(new ArrayList<>(List.of(game1, game2)));

        List<GameDTO> result = gameService.getAllGames();
        assert result.size() == 2;
        assert result.get(0).getName().equals("Game 1");
        assert result.get(1).getName().equals("Game 2");
    }

    @Test
    void getGameById_shouldReturnGameDTO_whenGameExists() {
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setName("Test Game");
        game.setCreatedAt(LocalDateTime.now());
        game.setUpdatedAt(LocalDateTime.now());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        GameDTO result = gameService.getGameById(gameId);
        assert result.getName().equals("Test Game");
    }

    @Test
    void getGameById_shouldReturnNotFoundException_whenGameDoesNotExist() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.getGameById(gameId);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + gameId);
        }
    }

    @Test
    void updateGame_shouldReturnUpdatedGameDTO_whenGameExists() {
        Long gameId = 1L;
        UpdateGameRequest updateGameRequest = new UpdateGameRequest();
        updateGameRequest.setName("Updated Game Name");

        Game existingGame = new Game();
        existingGame.setId(gameId);
        existingGame.setName("Old Game Name");
        existingGame.setCreatedAt(LocalDateTime.now());
        existingGame.setUpdatedAt(LocalDateTime.now());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(any(Game.class))).thenReturn(existingGame);

        GameDTO result = gameService.updateGame(gameId, updateGameRequest);
        assert result.getName().equals("Updated Game Name");
    }

    @Test
    void updateGame_shouldReturnNotFoundException_whenGameDoesNotExist() {
        Long gameId = 1L;
        UpdateGameRequest updateGameRequest = new UpdateGameRequest();
        updateGameRequest.setName("Updated Game Name");

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.updateGame(gameId, updateGameRequest);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + gameId);
        }
    }

    @Test
    void deleteGame_shouldDeleteGame_whenGameExists() {
        Long gameId = 1L;
        Game existingGame = new Game();
        existingGame.setId(gameId);
        existingGame.setName("Test Game");
        existingGame.setCreatedAt(LocalDateTime.now());
        existingGame.setUpdatedAt(LocalDateTime.now());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));

        gameService.deleteGame(gameId);
    }

    @Test
    void deleteGame_shouldThrowNotFoundException_whenGameDoesNotExist() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.deleteGame(gameId);
        } catch (CustomExceptions.GameNotFoundException e) {
            assert e.getMessage().equals("Game not found with id: " + gameId);
        }
    }
}
