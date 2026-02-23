package com.gameproject.controller;

import com.gameproject.service.FightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fight")
public class FightController {

    private final FightService fightService;

    public FightController(FightService fightService) {
        this.fightService = fightService;
    }

    @PostMapping("/{aId}/vs/{bId}")
    public ResponseEntity<String> fight(
            @PathVariable Long aId,
            @PathVariable Long bId) {

        return ResponseEntity.ok(fightService.fight(aId, bId));
    }
}

