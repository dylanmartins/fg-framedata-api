package com.fgc.framedata_api.request;

public class UpdateCharacterRequest {
    private String name;

    public UpdateCharacterRequest() {
    }

    public UpdateCharacterRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
