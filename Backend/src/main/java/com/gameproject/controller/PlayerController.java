package com.gameproject.controller;

import com.gameproject.dto.CreatePlayerRequest;
import com.gameproject.entity.Player;
import com.gameproject.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Player create(@RequestBody CreatePlayerRequest req) {
        return service.createPlayer(req.getName());
    }

    @PostMapping("/login")
    public Player login(@RequestBody CreatePlayerRequest req) {
        return service.login(req.getName());
    }

    @GetMapping("/{id}")
    public Player get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<Player> getAll() {
        return service.getAll();
    }
}
