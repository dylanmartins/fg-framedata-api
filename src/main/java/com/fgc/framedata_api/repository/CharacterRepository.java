package com.fgc.framedata_api.repository;

import com.fgc.framedata_api.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
