package com.fgc.framedata_api.service.unit;

import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.model.GameDTO;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
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
        GameDTO gameDTO = new GameDTO(
                gameId,
                "Updated Game",
                null,
                null,
                null
        );
        gameDTO.setName("Updated Game");

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.updateGame(gameId, gameDTO);
        } catch (ResponseStatusException e) {
            assert e.getStatusCode() == HttpStatus.NOT_FOUND;
        }
    }

    @Test
    void deleteGame_shouldThrowException_whenGameNotFound() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.deleteGame(gameId);
        } catch (ResponseStatusException e) {
            assert e.getStatusCode() == HttpStatus.NOT_FOUND;
        }
    }

    @Test
    void getGameById_shouldThrowException_whenGameNotFound() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        try {
            gameService.getGameById(gameId);
        } catch (ResponseStatusException e) {
            assert e.getStatusCode() == HttpStatus.NOT_FOUND;
        }
    }

    @Test
    void addGame_shouldReturnGameDTO_whenGameIsAdded() {
        GameDTO gameDTO = new GameDTO(
                null,
                "Test Game",
                new ArrayList<>(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Game game = new Game();
        game.setName(gameDTO.getName());
        game.setCreatedAt(LocalDateTime.now());
        game.setUpdatedAt(LocalDateTime.now());

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        GameDTO result = gameService.addGame(gameDTO);
        assert result.getName().equals("Test Game");
    }
}
