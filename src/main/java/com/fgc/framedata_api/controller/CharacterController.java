package com.fgc.framedata_api.controller;

import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.request.CreateCharacterRequest;
import com.fgc.framedata_api.response.ListCharacterResponse;
import com.fgc.framedata_api.service.CharacterService;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"/characters", "/characters/"})
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ListCharacterResponse getAllCharacters() {
        List<CharacterDTO> characters = characterService.getAllCharacters();
        return new ListCharacterResponse(characters);
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> addCharacter(@RequestBody CreateCharacterRequest createCharacterRequest) {
        try {
            CharacterDTO createdCharacter = characterService.addCharacter(createCharacterRequest);
            return ResponseEntity.status(201).body(createdCharacter);
        } catch (CustomExceptions.GameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
