package com.fgc.framedata_api.response;

import com.fgc.framedata_api.dto.GameDTO;

import java.util.List;

public class ListGameResponse {

    private List<GameDTO> games;

    public ListGameResponse() {
    }

    public ListGameResponse(List<GameDTO> games) {
        this.games = games;
    }

    public List<GameDTO> getGames() {
        return games;
    }

    public void setGames(List<GameDTO> games) {
        this.games = games;
    }
}
