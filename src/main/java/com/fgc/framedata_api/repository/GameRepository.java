package com.fgc.framedata_api.repository;

import com.fgc.framedata_api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
