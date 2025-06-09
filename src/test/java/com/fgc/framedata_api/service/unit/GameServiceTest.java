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
}
