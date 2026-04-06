package com.fgc.framedata_api.request;

public class CreateCharacterRequest {
    private String name;
    private Long gameId;
    private String patchVersion;

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

    public String getPatchVersion() {
        return patchVersion;
    }

    public void setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }
}
