package com.gameproject.service;

import com.gameproject.entity.GameCharacter;
import com.gameproject.entity.Player;
import com.gameproject.repository.GameCharacterRepository;
import com.gameproject.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final GameCharacterRepository characterRepo;
    private final PlayerRepository playerRepo;

    private static final String BASE_PATH = "/uploads/";

    public CharacterServiceImpl(
            GameCharacterRepository characterRepo,
            PlayerRepository playerRepo
    ) {
        this.characterRepo = characterRepo;
        this.playerRepo = playerRepo;
    }

    @Override
    public List<GameCharacter> getAll() {

        List<GameCharacter> list = characterRepo.findAll();

        list.forEach(this::attachImage);

        return list;
    }

    @Override
    @Transactional
    public GameCharacter assignCharacterToPlayer(Long characterId, Long playerId) {

        GameCharacter character = characterRepo.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (player.getCharacter() != null) {
            GameCharacter old = player.getCharacter();
            old.setPlayer(null);
            characterRepo.save(old);
        }

        if (character.getPlayer() != null) {
            character.getPlayer().setCharacter(null);
        }

        player.setCharacter(character);     // 👈 CRITIC
        character.setPlayer(player);        // 👈 ambele părți

        return character;

    }

    @Override
    public List<GameCharacter> getCharactersForPlayer(Long playerId) {

        List<GameCharacter> list = characterRepo.findAll()
                .stream()
                .filter(c -> c.getPlayer() != null &&
                        c.getPlayer().getId().equals(playerId))
                .collect(java.util.stream.Collectors.toList());

        list.forEach(this::attachImage);

        return list;
    }

    // 🔥 setează imaginea din static/uploads/<id>.png
    private void attachImage(GameCharacter character) {
        if (character != null && character.getId() != null) {
            character.setImageUrl(BASE_PATH + character.getId() + ".png");
        }
    }
}
