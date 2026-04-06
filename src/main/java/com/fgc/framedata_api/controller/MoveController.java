package com.fgc.framedata_api.controller;

import com.fgc.framedata_api.dto.MoveDTO;
import com.fgc.framedata_api.request.CreateMoveRequest;
import com.fgc.framedata_api.request.UpdateMoveRequest;
import com.fgc.framedata_api.response.ListMoveResponse;
import com.fgc.framedata_api.service.MoveService;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/characters/{characterId}/moves")
public class MoveController {

    private final MoveService moveService;

    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @GetMapping
    public ResponseEntity<ListMoveResponse> getAllMovesByCharacter(@PathVariable Long characterId) {
        try {
            List<MoveDTO> moves = moveService.getAllMovesByCharacter(characterId);
            return ResponseEntity.ok(new ListMoveResponse(moves));
        } catch (CustomExceptions.CharacterNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<MoveDTO> addMove(@PathVariable Long characterId, @RequestBody CreateMoveRequest request) {
        try {
            MoveDTO createdMove = moveService.addMove(characterId, request);
            return ResponseEntity.status(201).body(createdMove);
        } catch (CustomExceptions.CharacterNotFoundException | CustomExceptions.MoveNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoveDTO> getMoveById(@PathVariable Long characterId, @PathVariable Long id) {
        try {
            MoveDTO move = moveService.getMoveById(id);
            return ResponseEntity.ok(move);
        } catch (CustomExceptions.MoveNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoveDTO> updateMove(@PathVariable Long characterId, @PathVariable Long id, @RequestBody UpdateMoveRequest request) {
        try {
            MoveDTO updatedMove = moveService.updateMove(id, request);
            return ResponseEntity.ok(updatedMove);
        } catch (CustomExceptions.MoveNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMove(@PathVariable Long characterId, @PathVariable Long id) {
        try {
            moveService.deleteMove(id);
            return ResponseEntity.noContent().build();
        } catch (CustomExceptions.MoveNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
