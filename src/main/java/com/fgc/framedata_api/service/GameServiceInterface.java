package com.fgc.framedata_api.service;

import com.fgc.framedata_api.dto.GameDTO;
import com.fgc.framedata_api.request.CreateGameRequest;
import com.fgc.framedata_api.request.UpdateGameRequest;

import java.util.List;

public interface GameServiceInterface {
    GameDTO addGame(CreateGameRequest createRequest);
    List<GameDTO> getAllGames();
    GameDTO getGameById(Long id);
    GameDTO updateGame(Long id, UpdateGameRequest updateGameRequest);
    void deleteGame(Long id);
}
