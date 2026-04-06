package com.fgc.framedata_api.service;

import com.fgc.framedata_api.dto.MoveDTO;
import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.model.Move;
import com.fgc.framedata_api.repository.CharacterRepository;
import com.fgc.framedata_api.repository.MoveRepository;
import com.fgc.framedata_api.request.CreateMoveRequest;
import com.fgc.framedata_api.request.UpdateMoveRequest;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveService implements MoveServiceInterface {

    private final MoveRepository moveRepository;
    private final CharacterRepository characterRepository;

    public MoveService(MoveRepository moveRepository, CharacterRepository characterRepository) {
        this.moveRepository = moveRepository;
        this.characterRepository = characterRepository;
    }

    public MoveDTO addMove(Long characterId, CreateMoveRequest request) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new CustomExceptions.CharacterNotFoundException("Character not found with id: " + characterId));

        Move move = new Move();
        move.setCharacter(character);
        move.setName(request.getName());
        move.setInput(request.getInput());
        move.setMoveType(request.getMoveType());
        move.setSuperArtLevel(request.getSuperArtLevel());
        move.setDamage(request.getDamage());
        move.setChipDamage(request.getChipDamage());
        move.setStartupFrames(request.getStartupFrames());
        move.setActiveFrames(request.getActiveFrames());
        move.setRecoveryFrames(request.getRecoveryFrames());
        move.setOnHitAdvantage(request.getOnHitAdvantage());
        move.setOnBlockAdvantage(request.getOnBlockAdvantage());
        move.setOnCounterHitAdvantage(request.getOnCounterHitAdvantage());
        move.setOnPunishCounterAdvantage(request.getOnPunishCounterAdvantage());
        move.setDriveRushOnHit(request.getDriveRushOnHit());
        move.setDriveRushOnBlock(request.getDriveRushOnBlock());
        move.setHasArmor(request.getHasArmor() != null ? request.getHasArmor() : false);
        move.setArmorHits(request.getArmorHits());
        move.setCancelOptions(request.getCancelOptions());
        move.setNotes(request.getNotes());

        if (request.getParentMoveId() != null) {
            Move parentMove = moveRepository.findById(request.getParentMoveId())
                    .orElseThrow(() -> new CustomExceptions.MoveNotFoundException("Parent move not found with id: " + request.getParentMoveId()));
            move.setParentMove(parentMove);
        }

        move = moveRepository.save(move);
        return mapToDTO(move);
    }

    public List<MoveDTO> getAllMovesByCharacter(Long characterId) {
        if (!characterRepository.existsById(characterId)) {
            throw new CustomExceptions.CharacterNotFoundException("Character not found with id: " + characterId);
        }
        return moveRepository.findAllByCharacterId(characterId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MoveDTO getMoveById(Long id) {
        Move move = moveRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.MoveNotFoundException("Move not found with id: " + id));
        return mapToDTO(move);
    }

    public MoveDTO updateMove(Long id, UpdateMoveRequest request) {
        Move move = moveRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.MoveNotFoundException("Move not found with id: " + id));

        if (request.getName() != null) move.setName(request.getName());
        if (request.getInput() != null) move.setInput(request.getInput());
        if (request.getMoveType() != null) move.setMoveType(request.getMoveType());
        if (request.getSuperArtLevel() != null) move.setSuperArtLevel(request.getSuperArtLevel());
        if (request.getDamage() != null) move.setDamage(request.getDamage());
        if (request.getChipDamage() != null) move.setChipDamage(request.getChipDamage());
        if (request.getStartupFrames() != null) move.setStartupFrames(request.getStartupFrames());
        if (request.getActiveFrames() != null) move.setActiveFrames(request.getActiveFrames());
        if (request.getRecoveryFrames() != null) move.setRecoveryFrames(request.getRecoveryFrames());
        if (request.getOnHitAdvantage() != null) move.setOnHitAdvantage(request.getOnHitAdvantage());
        if (request.getOnBlockAdvantage() != null) move.setOnBlockAdvantage(request.getOnBlockAdvantage());
        if (request.getOnCounterHitAdvantage() != null) move.setOnCounterHitAdvantage(request.getOnCounterHitAdvantage());
        if (request.getOnPunishCounterAdvantage() != null) move.setOnPunishCounterAdvantage(request.getOnPunishCounterAdvantage());
        if (request.getDriveRushOnHit() != null) move.setDriveRushOnHit(request.getDriveRushOnHit());
        if (request.getDriveRushOnBlock() != null) move.setDriveRushOnBlock(request.getDriveRushOnBlock());
        if (request.getHasArmor() != null) move.setHasArmor(request.getHasArmor());
        if (request.getArmorHits() != null) move.setArmorHits(request.getArmorHits());
        if (request.getCancelOptions() != null) move.setCancelOptions(request.getCancelOptions());
        if (request.getNotes() != null) move.setNotes(request.getNotes());

        if (request.getParentMoveId() != null) {
            Move parentMove = moveRepository.findById(request.getParentMoveId())
                    .orElseThrow(() -> new CustomExceptions.MoveNotFoundException("Parent move not found with id: " + request.getParentMoveId()));
            move.setParentMove(parentMove);
        }

        move = moveRepository.save(move);
        return mapToDTO(move);
    }

    public void deleteMove(Long id) {
        Move move = moveRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.MoveNotFoundException("Move not found with id: " + id));
        moveRepository.deleteById(move.getId());
    }

    private MoveDTO mapToDTO(Move move) {
        MoveDTO dto = new MoveDTO();
        dto.setId(move.getId());
        dto.setCharacterId(move.getCharacter().getId());
        dto.setName(move.getName());
        dto.setInput(move.getInput());
        dto.setMoveType(move.getMoveType());
        dto.setSuperArtLevel(move.getSuperArtLevel());
        dto.setDamage(move.getDamage());
        dto.setChipDamage(move.getChipDamage());
        dto.setStartupFrames(move.getStartupFrames());
        dto.setActiveFrames(move.getActiveFrames());
        dto.setRecoveryFrames(move.getRecoveryFrames());
        dto.setOnHitAdvantage(move.getOnHitAdvantage());
        dto.setOnBlockAdvantage(move.getOnBlockAdvantage());
        dto.setOnCounterHitAdvantage(move.getOnCounterHitAdvantage());
        dto.setOnPunishCounterAdvantage(move.getOnPunishCounterAdvantage());
        dto.setDriveRushOnHit(move.getDriveRushOnHit());
        dto.setDriveRushOnBlock(move.getDriveRushOnBlock());
        dto.setHasArmor(move.getHasArmor());
        dto.setArmorHits(move.getArmorHits());
        dto.setCancelOptions(move.getCancelOptions());
        dto.setNotes(move.getNotes());
        if (move.getParentMove() != null) {
            dto.setParentMoveId(move.getParentMove().getId());
        }
        return dto;
    }
}
