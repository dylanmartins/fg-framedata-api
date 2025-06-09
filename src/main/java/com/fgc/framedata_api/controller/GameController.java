package com.fgc.framedata_api.controller;

import com.fgc.framedata_api.dto.GameDTO;
import com.fgc.framedata_api.request.CreateGameRequest;
import com.fgc.framedata_api.request.UpdateGameRequest;
import com.fgc.framedata_api.response.ListGameResponse;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.service.GameService;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"/games", "/games/"})
public class GameController {

    private final GameService gameService;

    public GameController(GameRepository gameRepository, GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ListGameResponse getAllGames() {
        List<GameDTO> gameDTOs = gameService.getAllGames();
        return new ListGameResponse(gameDTOs);
    }

    @PostMapping
    public ResponseEntity<GameDTO> addGame(@RequestBody CreateGameRequest createGameRequest) {
        GameDTO createdGame = gameService.addGame(createGameRequest);
        return ResponseEntity.status(201).body(createdGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody UpdateGameRequest updateGameRequest) {
        try {
            GameDTO game = gameService.updateGame(id, updateGameRequest);
            return ResponseEntity.ok(game);
        } catch (CustomExceptions.GameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.noContent().build();
        } catch (CustomExceptions.GameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long id) {
        try {
            GameDTO game = gameService.getGameById(id);
            return ResponseEntity.ok(game);
        } catch (CustomExceptions.GameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
