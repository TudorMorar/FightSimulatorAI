package com.gameproject.service;

import com.gameproject.dto.CreateCharacterRequest;
import com.gameproject.entity.GameCharacter;

import java.util.List;

public interface CharacterGeneratorService {
    GameCharacter generateCharacter(CreateCharacterRequest req);
    List<GameCharacter> getAll();
}
