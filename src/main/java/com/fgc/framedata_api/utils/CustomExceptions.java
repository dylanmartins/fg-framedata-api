package com.fgc.framedata_api.utils;

public class CustomExceptions {

    public static class GameNotFoundException extends RuntimeException {
        public GameNotFoundException(String message) {
            super(message);
        }
    }

    public static class CharacterNotFoundException extends RuntimeException {
        public CharacterNotFoundException(String message) {
            super(message);
        }
    }
}
