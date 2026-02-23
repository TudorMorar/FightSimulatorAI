package com.gameproject.controller;

import com.gameproject.dto.ProgressionDto;
import com.gameproject.entity.Player;
import com.gameproject.service.PlayerService;
import com.gameproject.service.ProgressionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProgressController {

    private final PlayerService playerService;
    private final ProgressionService progressionService;

    public ProgressController(PlayerService playerService,
                              ProgressionService progressionService) {
        this.playerService = playerService;
        this.progressionService = progressionService;
    }

    @GetMapping("/player/{id}/progress")
    public ProgressionDto getProgress(@PathVariable Long id) {
        Player p = playerService.getById(id);
        return progressionService.getProgress(p);
    }
}




