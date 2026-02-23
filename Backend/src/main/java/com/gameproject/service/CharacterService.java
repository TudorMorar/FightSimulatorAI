package com.gameproject.service;

import com.gameproject.entity.GameCharacter;

import java.util.List;

public interface CharacterService {

    List<GameCharacter> getAll();

    GameCharacter assignCharacterToPlayer(Long characterId, Long playerId);

    List<GameCharacter> getCharactersForPlayer(Long playerId);


}
