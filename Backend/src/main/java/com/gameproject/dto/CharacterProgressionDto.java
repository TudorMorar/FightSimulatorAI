package com.gameproject.dto;

public class CharacterProgressionDto {

    private String name;
    private int level;
    private int xp;
    private int requiredXp;

    public CharacterProgressionDto(String name,
                                   int level,
                                   int xp,
                                   int requiredXp) {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.requiredXp = requiredXp;
    }

    public CharacterProgressionDto() {
    }

    public String getName() {
        return name;
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
}

