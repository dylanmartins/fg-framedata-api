package com.fgc.framedata_api.repository;

import com.fgc.framedata_api.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findAllByGameId(Long gameId);
}
