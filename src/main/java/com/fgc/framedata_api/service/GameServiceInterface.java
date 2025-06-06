package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.GameDTO;

import java.util.List;

public interface GameServiceInterface {
    GameDTO addGame(GameDTO gameDTO);
    List<GameDTO> getAllGames();
    GameDTO getGameById(Long id);
    GameDTO updateGame(Long id, GameDTO gameDTO);
    void deleteGame(Long id);
}
