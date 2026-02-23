package com.gameproject.dto;

public class ProgressionDto {

    private String playerName;
    private int level;
    private int xp;
    private int requiredXp;

    // 🔥 NOU
    private int stamina;
    private int maxStamina;

    private CharacterProgressionDto character;

    public ProgressionDto(String playerName,
                          int level,
                          int xp,
                          int requiredXp,
                          int stamina,
                          int maxStamina,
                          CharacterProgressionDto character) {
        this.playerName = playerName;
        this.level = level;
        this.xp = xp;
        this.requiredXp = requiredXp;
        this.stamina = stamina;
        this.maxStamina = maxStamina;
        this.character = character;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getRequiredXp() {
        return requiredXp;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public CharacterProgressionDto getCharacter() {
        return character;
    }
}
