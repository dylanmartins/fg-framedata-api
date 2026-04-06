package com.fgc.framedata_api.response;

import com.fgc.framedata_api.dto.MoveDTO;

import java.util.List;

public class ListMoveResponse {

    private List<MoveDTO> moves;

    public ListMoveResponse() {}

    public ListMoveResponse(List<MoveDTO> moves) {
        this.moves = moves;
    }

    public List<MoveDTO> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveDTO> moves) {
        this.moves = moves;
    }
}
