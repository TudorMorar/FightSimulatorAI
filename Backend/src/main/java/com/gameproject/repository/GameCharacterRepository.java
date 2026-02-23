package com.gameproject.repository;

import com.gameproject.entity.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    @Query("SELECT c FROM GameCharacter c WHERE c.player.id = :playerId")
    GameCharacter findByPlayerId(Long playerId);
}

