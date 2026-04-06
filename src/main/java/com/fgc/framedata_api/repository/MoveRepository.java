package com.fgc.framedata_api.repository;

import com.fgc.framedata_api.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findAllByCharacterId(Long characterId);
    List<Move> findAllByParentMoveId(Long parentMoveId);
}
