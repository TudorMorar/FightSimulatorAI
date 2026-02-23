package com.gameproject.service;

import com.gameproject.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StaminaRegenServiceImpl implements StaminaRegenService {

    private final PlayerRepository playerRepo;

    public StaminaRegenServiceImpl(PlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    // 🔁 rulează la fiecare 2 minute
    @Override
    @Scheduled(fixedRate = 120_000)
    @Transactional
    public void regenerateStamina() {

        playerRepo.findAll().forEach(player -> {
            if (player.getStamina() < player.getMaxStamina()) {
                player.setStamina(player.getStamina() + 1);
            }
        });
    }
}
