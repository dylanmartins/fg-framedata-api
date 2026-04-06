package com.fgc.framedata_api.request;

public class UpdateCharacterRequest {
    private String name;
    private String patchVersion;

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

    public String getPatchVersion() {
        return patchVersion;
    }

    public void setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }
}
