package com.ecs160.hw3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class HashtaggingController {
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/hashtag")
    public String hashtag(@RequestBody HashtagRequest request) {
        String modelName = "llama3.2";
        String prompt = "Generate exactly one hashtag (no spaces, no explanation) for: " + request.getPostContent();
        try {

            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            ObjectNode payload = mapper.createObjectNode();
            payload.put("model", modelName);
            payload.put("prompt", prompt);
            payload.put("stream", false);
            String jsonInputString = mapper.writeValueAsString(payload);

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Check HTTP status
            int statusCode = conn.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException();
            }

            // Parse response
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                JsonNode jsonResponse = mapper.readTree(in);
                String responseText = jsonResponse.get("response").asText().trim();

                // Format hashtag
                if (!responseText.startsWith("#")) {
                    responseText = "#" + responseText;
                }
                return responseText.replaceAll("\\s+", "");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    static class HashtagRequest {
        private String postContent;

        // Getters/setters
        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }
    }
}
