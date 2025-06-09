package com.fgc.framedata_api.request;

public class CreateCharacterRequest {
    private String name;
    private Long gameId;

    public void setName(String name) {
        this.name = name;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public Long getGameId() {
        return gameId;
    }
}
