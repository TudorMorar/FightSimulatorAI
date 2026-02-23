package com.gameproject.service;

import com.gameproject.entity.Player;
import com.gameproject.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repo;

    public PlayerServiceImpl(PlayerRepository repo) {
        this.repo = repo;
    }

    // 🔹 Funcție comună de normalizare
    private String normalize(String name) {
        return name == null ? null : name.trim().toLowerCase();
    }

    @Override
    public Player createPlayer(String name) {
        String clean = normalize(name);

        if (clean == null || clean.isEmpty()) {
            throw new RuntimeException("Name cannot be empty");
        }

        if (repo.findByNameIgnoreCase(clean).isPresent()) {
            throw new RuntimeException("Player already exists");
        }

        Player p = new Player();
        p.setName(clean);
        p.setStamina(100);
        p.setMaxStamina(100);
        p.setWins(0);
        p.setLoses(0);
        p.setLevel(1);
        p.setXp(0);

        return repo.save(p);
    }

    @Override
    public Player login(String name) {
        String clean = normalize(name);

        return repo.findByNameIgnoreCase(clean)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    // 🔥 AICI ESTE FIXUL CRITIC
    @Override
    public Player getById(Long id) {
        Player p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        attachCharacterImage(p);

        return p;
    }

    @Override
    public Player getByName(String name) {
        String clean = normalize(name);

        Player p = repo.findByNameIgnoreCase(clean)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        attachCharacterImage(p);

        return p;
    }

    // 🔥 ȘI AICI
    @Override
    public List<Player> getAll() {
        List<Player> list = repo.findAll();

        list.forEach(this::attachCharacterImage);

        return list;
    }

    // 🔹 Helper intern
    private void attachCharacterImage(Player p) {
        if (p.getCharacter() != null && p.getCharacter().getId() != null) {
            p.getCharacter().setImageUrl("/uploads/" + p.getCharacter().getId() + ".png");
        }
    }
}
