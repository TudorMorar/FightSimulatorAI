package com.gameproject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class AIImageServiceImpl implements AIImageService {

    private static final String URL = "https://api.openai.com/v1/images/generations";

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String generateAndSave(String prompt, Long playerId) {

        try {
            String apiKey = System.getenv("OPENAI_API_KEY");
            if (apiKey == null) throw new RuntimeException("API key missing");

            String body = """
            {
              "model": "gpt-image-1",
              "prompt": "%s",
              "size": "512x512"
            }
            """.formatted(prompt.replace("\"","'"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> req = new HttpEntity<>(body, headers);

            ResponseEntity<String> res =
                    rest.exchange(URL, HttpMethod.POST, req, String.class);

            JsonNode node = mapper.readTree(res.getBody());

            // GPT-Image-1 returnează URL
            String imageUrl = node
                    .get("data")
                    .get(0)
                    .get("url")
                    .asText();

            // ====== SALVARE LOCALĂ ======
            Path dir = Path.of("uploads");
            Files.createDirectories(dir);

            Path file = dir.resolve("player_" + playerId + ".png");

            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Imagine salvată: " + file.toAbsolutePath());

            return "/uploads/" + file.getFileName();
        }
        catch (Exception e) {
            System.out.println("❌ AI IMAGE ERROR — folosesc default");
            e.printStackTrace();
            return "/uploads/default.png";
        }
    }
}
