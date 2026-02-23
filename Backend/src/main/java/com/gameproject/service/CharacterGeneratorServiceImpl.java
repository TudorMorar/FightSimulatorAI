package com.gameproject.service;

import com.gameproject.dto.CreateCharacterRequest;
import com.gameproject.entity.GameCharacter;
import com.gameproject.entity.Player;
import com.gameproject.repository.GameCharacterRepository;
import com.gameproject.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterGeneratorServiceImpl implements CharacterGeneratorService {

    private final GameCharacterRepository characterRepo;
    private final PlayerRepository playerRepo;
    private final AIImageService aiImageService;

    public CharacterGeneratorServiceImpl(GameCharacterRepository characterRepo,
                                         PlayerRepository playerRepo,
                                         AIImageService aiImageService) {
        this.characterRepo = characterRepo;
        this.playerRepo = playerRepo;
        this.aiImageService = aiImageService;
    }

    @Override
    public List<GameCharacter> getAll() {
        return characterRepo.findAll();
    }

    @Override
    @Transactional
    public GameCharacter generateCharacter(CreateCharacterRequest r) {

        try {

            Player player = playerRepo.findById(r.getPlayerId())
                    .orElseThrow(() -> new RuntimeException("Player not found"));

            if (player.getCharacter() != null) {
                throw new RuntimeException("Player already has a character!");
            }

            GameCharacter c = new GameCharacter();
            c.setPlayer(player);

            c.setName(makeName(r));

            int power = 70;
            int health = 100;
            int luck = 10;

            switch (r.getFood()) {
                case "Shaorma" -> power += 15;
                case "Pizza" -> health += 20;
                case "Burger" -> power += 10;
                case "Salată" -> luck += 20;
            }

            switch (r.getHobby()) {
                case "Gaming" -> luck += 15;
                case "Sport" -> health += 25;
                case "Meme-uri" -> luck += 20;
                case "Netflix" -> health += 10;
            }

            switch (r.getStyle()) {
                case "Tank" -> health += 40;
                case "Rapid" -> power += 10;
                case "Brutal" -> power += 25;
                case "Norocos" -> luck += 35;
            }

            switch (r.getEducation()) {
                case "Facultate" -> luck += 5;
                case "Master" -> luck += 10;
                case "Doctorat" -> luck += 15;
                case "Școala vieții" -> power += 20;
            }

            switch (r.getCar()) {
                case "BMW" -> power += 15;
                case "Mercedes" -> health += 20;
                case "Volkswagen" -> luck += 10;
                case "Dacia" -> health += 30;
            }

            switch (r.getDrink()) {
                case "Bere" -> health += 10;
                case "Vin" -> luck += 15;
                case "Whisky" -> power += 20;
                case "Cafea" -> luck += 10;
            }

            c.setPower(power);
            c.setHealth(health);
            c.setLuck(luck);

            String prompt = buildPrompt(r);
            String imageUrl = aiImageService.generateAndSave(prompt, player.getId());
            c.setImageUrl(imageUrl);

            return characterRepo.save(c);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Character generation failed: " + e.getMessage(), e);
        }
    }


    private String makeName(CreateCharacterRequest r) {

        String prefix = switch (r.getStyle()) {
            case "Tank" -> "Colosul";
            case "Rapid" -> "Fulgerul";
            case "Brutal" -> "Măcelarul";
            case "Norocos" -> "Alesul Sorții";
            default -> "Eroul";
        };

        String core = switch (r.getFood()) {
            case "Shaorma" -> "Shaormelor";
            case "Pizza" -> "Pizzelor";
            case "Burger" -> "Burgerilor";
            case "Salată" -> "Frunzelor";
            default -> "Gustosului";
        };

        String suffix = switch (r.getHobby()) {
            case "Gaming" -> "Controllerului Etern";
            case "Sport" -> "Arenei";
            case "Meme-uri" -> "Lordul Meme-urilor";
            case "Netflix" -> "Stăpânul Serialelor";
            default -> "Plimbărețul";
        };

        return String.join(" ", prefix, "al", core, suffix).trim();
    }


    private String buildPrompt(CreateCharacterRequest r) {

        return String.format(
                "Create a colorful fantasy game character portrait. " +
                        "Highly detailed, cinematic lighting, friendly expression. " +
                        "Digital illustration, game art style. " +
                        "Character style: %s. Favorite food: %s. Hobby: %s. Car: %s. Drink: %s. Education level: %s.",
                r.getStyle(),
                r.getFood(),
                r.getHobby(),
                r.getCar(),
                r.getDrink(),
                r.getEducation()
        );
    }

}
