package com.gameproject.service;

import com.gameproject.dto.CharacterProgressionDto;
import com.gameproject.dto.ProgressionDto;
import com.gameproject.entity.GameCharacter;
import com.gameproject.entity.Player;
import org.springframework.stereotype.Service;

@Service
public class ProgressionServiceImpl implements ProgressionService {

    // ================= PLAYER =================

    @Override
    public int getPlayerRequiredXp(Player player) {
        // 100 × level²
        return 100 * player.getLevel() * player.getLevel();
    }

    @Override
    public void applyPlayerXp(Player player, int gainedXp) {

        player.setXp(player.getXp() + gainedXp);

        while (player.getXp() >= getPlayerRequiredXp(player)) {
            player.setXp(player.getXp() - getPlayerRequiredXp(player));
            player.setLevel(player.getLevel() + 1);
        }
    }

    // ================= CHARACTER =================

    @Override
    public int getCharacterRequiredXp(GameCharacter character) {
        // 100 × 1.5^(level-1)
        return (int) Math.ceil(
                100 * Math.pow(1.5, character.getLevel() - 1)
        );
    }

    @Override
    public void applyCharacterXp(GameCharacter character, int gainedXp) {

        character.setXp(character.getXp() + gainedXp);

        while (character.getXp() >= getCharacterRequiredXp(character)) {
            character.setXp(
                    character.getXp() - getCharacterRequiredXp(character)
            );
            character.setLevel(character.getLevel() + 1);
        }
    }

    // ================= BATTLE =================

    @Override
    public void awardXp(Player winner, Player loser) {

        applyPlayerXp(winner, 30);
        applyPlayerXp(loser, 10);

        if (winner.getCharacter() != null) {
            applyCharacterXp(winner.getCharacter(), 50);
        }

        if (loser.getCharacter() != null) {
            applyCharacterXp(loser.getCharacter(), 10);
        }
    }

    // ================= PROGRESS QUERY =================

    @Override
    public ProgressionDto getProgress(Player player) {

        CharacterProgressionDto characterDto = null;

        if (player.getCharacter() != null) {
            GameCharacter c = player.getCharacter();
            characterDto = new CharacterProgressionDto(
                    c.getName(),
                    c.getLevel(),
                    c.getXp(),
                    getCharacterRequiredXp(c)
            );
        }

        return new ProgressionDto(
                player.getName(),
                player.getLevel(),
                player.getXp(),
                getPlayerRequiredXp(player),

                // 🔥 NOU
                player.getStamina(),
                player.getMaxStamina(),

                characterDto
        );
    }

}
