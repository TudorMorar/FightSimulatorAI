package com.gameproject.service;

import com.gameproject.dto.ProgressionDto;
import com.gameproject.entity.GameCharacter;
import com.gameproject.entity.Player;

public interface ProgressionService {

    // ===== GAME LOGIC =====

    void awardXp(Player winner, Player loser);

    void applyPlayerXp(Player player, int gainedXp);

    void applyCharacterXp(GameCharacter character, int gainedXp);

    int getPlayerRequiredXp(Player player);

    int getCharacterRequiredXp(GameCharacter character);

    // ===== QUERY / API =====

    ProgressionDto getProgress(Player player);
}
