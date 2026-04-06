package com.fgc.framedata_api.service;

import com.fgc.framedata_api.dto.MoveDTO;
import com.fgc.framedata_api.request.CreateMoveRequest;
import com.fgc.framedata_api.request.UpdateMoveRequest;

import java.util.List;

public interface MoveServiceInterface {
    MoveDTO addMove(Long characterId, CreateMoveRequest request);
    List<MoveDTO> getAllMovesByCharacter(Long characterId);
    MoveDTO getMoveById(Long id);
    MoveDTO updateMove(Long id, UpdateMoveRequest request);
    void deleteMove(Long id);
}
