package com.gameproject.controller;

import com.gameproject.entity.GameCharacter;
import com.gameproject.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }


    @GetMapping
    public List<GameCharacter> getAll() {
        return service.getAll();
    }

    @PostMapping("/assign/{characterId}/to/{playerId}")
    public GameCharacter assignToPlayer(
            @PathVariable Long characterId,
            @PathVariable Long playerId
    ) {
        return service.assignCharacterToPlayer(characterId, playerId);
    }

    @GetMapping("/player/{playerId}")
    public List<GameCharacter> getByPlayer(@PathVariable Long playerId) {
        return service.getCharactersForPlayer(playerId);
    }
}
