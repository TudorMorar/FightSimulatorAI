package com.gameproject.controller;

import com.gameproject.dto.CreateCharacterRequest;
import com.gameproject.entity.GameCharacter;
import com.gameproject.service.CharacterGeneratorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generator")
public class CharacterGenerationController {

    private final CharacterGeneratorService service;

    public CharacterGenerationController(CharacterGeneratorService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public GameCharacter generate(@RequestBody CreateCharacterRequest req) {
        return service.generateCharacter(req);
    }
}
