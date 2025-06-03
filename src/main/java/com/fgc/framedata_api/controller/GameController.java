package com.fgc.framedata_api.controller;

import com.fgc.framedata_api.model.GameDTO;
import com.fgc.framedata_api.model.GamesResponseDTO;
import com.fgc.framedata_api.repository.GameRepository;
import com.fgc.framedata_api.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/games", "/games/"})
public class GameController {

    private final GameRepository gameRepository;
    private final GameService gameService;

    public GameController(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    @GetMapping
    public GamesResponseDTO getAllGames() {
        List<GameDTO> gameDTOs = gameService.getAllGames();
        return new GamesResponseDTO(gameDTOs);
    }

    @PostMapping
    public ResponseEntity<GameDTO> addGame(@RequestBody GameDTO gameDTO) {
        GameDTO createdGame = gameService.addGame(gameDTO);
        return ResponseEntity.status(201).body(createdGame);
    }

    @PutMapping("/{id}")
    public GameDTO updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        return gameService.updateGame(id, gameDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public GameDTO getGameById(@PathVariable Long id) {
        return gameService.getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }
}
