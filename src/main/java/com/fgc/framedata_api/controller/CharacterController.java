package com.fgc.framedata_api.controller;

import com.fgc.framedata_api.dto.CharacterDTO;
import com.fgc.framedata_api.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/characters", "/characters/"})
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getAllCharacters() {
        List<CharacterDTO> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> addCharacter(@RequestBody CharacterDTO characterDTO) {
        CharacterDTO createdCharacter = characterService.addCharacter(characterDTO);
        return ResponseEntity.status(201).body(createdCharacter);
    }
}
