package com.ecs160.hw2;

import org.springframework.web.bind.annotation.*;
import com.github.ollama4j.OllamaClient;
import com.github.ollama4j.OllamaResult;

@RestController
public class HashtaggingController {
    @PostMapping("/hashtag")
    public String hashtag(@RequestBody HashtagRequest request) {
        try {
            OllamaClient client = new OllamaClient("http://localhost:11434");
            String prompt = "Generate one hashtag for: " + request.getPostContent();
            OllamaResult result = client.generate("llama3", prompt);
            return "#" + result.getOutput().trim();
        } catch (Exception e) {
            return "#bskypost"; // Fallback
        }
    }

    static class HashtagRequest {
        private String postContent;
        // Getters/setters
    }
}
