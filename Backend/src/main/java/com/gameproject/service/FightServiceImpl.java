package com.gameproject.service;

import com.gameproject.entity.GameCharacter;
import com.gameproject.entity.Player;
import com.gameproject.repository.GameCharacterRepository;
import com.gameproject.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FightServiceImpl implements FightService {

    private final PlayerRepository playerRepo;
    private final GameCharacterRepository characterRepo;
    private final ProgressionService progressionService;

    public FightServiceImpl(PlayerRepository playerRepo,
                            GameCharacterRepository characterRepo,
                            ProgressionService progressionService) {
        this.playerRepo = playerRepo;
        this.characterRepo = characterRepo;
        this.progressionService = progressionService;
    }

    @Override
    @Transactional
    public String fight(Long aId, Long bId) {

        // 🔹 Load players (profile data only)
        Player a = playerRepo.findById(aId)
                .orElseThrow(() -> new RuntimeException("Player A not found"));

        Player b = playerRepo.findById(bId)
                .orElseThrow(() -> new RuntimeException("Player B not found"));

        if (a.getId().equals(b.getId()))
            return "Un jucător nu poate lupta singur!";

        if (a.getStamina() <= 0 || b.getStamina() <= 0)
            return "Unul dintre jucători nu are stamina!";

        // 🔥 Load REAL characters from DB (not from Player cache)
        GameCharacter ca = characterRepo.findByPlayerId(a.getId());
        GameCharacter cb = characterRepo.findByPlayerId(b.getId());

        if (ca == null || cb == null)
            return "Unul dintre jucători nu are personaj!";

        // 🔹 consume stamina
        a.setStamina(a.getStamina() - 1);
        a.setFights(a.getFights() + 1);

        // 🔹 calculate base score
        double scoreA = ca.getPower() * ca.getHealth();
        double scoreB = cb.getPower() * cb.getHealth();

        // 🔹 luck bonus
        if (Math.random() < 0.3)
            scoreA *= 1 + (ca.getLuck() / 100.0);

        if (Math.random() < 0.3)
            scoreB *= 1 + (cb.getLuck() / 100.0);

        Player winner;
        Player loser;

        if (scoreA == scoreB) {
            winner = Math.random() < 0.5 ? a : b;
            loser = (winner == a) ? b : a;
        }
        else if (scoreA > scoreB) {
            winner = a;
            loser = b;
        }
        else {
            winner = b;
            loser = a;
        }

        if (winner == a) {
            a.setWins(a.getWins() + 1);
            progressionService.applyPlayerXp(a, 30);
            if (a.getCharacter() != null)
                progressionService.applyCharacterXp(a.getCharacter(), 50);
        } else {
            a.setLoses(a.getLoses() + 1);
            progressionService.applyPlayerXp(a, 10);
            if (a.getCharacter() != null)
                progressionService.applyCharacterXp(a.getCharacter(), 10);
        }

        // 🔹 Persist changes
        playerRepo.save(a);

        return "🏆 Winner: " + winner.getName();
    }
}
