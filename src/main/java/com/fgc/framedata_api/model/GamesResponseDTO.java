package com.fgc.framedata_api.model;

import java.util.List;

public class GamesResponseDTO {

    private List<GameDTO> games;

    public GamesResponseDTO() {
    }

    public GamesResponseDTO(List<GameDTO> games) {
        this.games = games;
    }

    public List<GameDTO> getGames() {
        return games;
    }

    public void setGames(List<GameDTO> games) {
        this.games = games;
    }
}
