package com.ecs160.hw3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class ModerationController {
    private final RestTemplate restTemplate;
    private static final List<String> bannedWords = List.of("illegal", "fraud", "scam",
            "exploit", "dox", "swatting", "hack", "crypto", "bots");

    @Autowired
    public ModerationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/moderate")
    public String moderate(@RequestBody MyRequest request) {
        // Moderation logic
        if (checkBannedWords(request.getPostContent())) {
            return "FAILED";
        } else {
            try {
                return restTemplate.postForObject(
                        "http://localhost:30001/hashtag",
                        request,
                        String.class
                );
            } catch (RestClientException e) {
                return "#bskypost";
            }
        }
    }

    private boolean checkBannedWords(String content) {
        String lowerCase = content.toLowerCase();
        return bannedWords.stream()
                .anyMatch(word -> lowerCase.matches(".*\\b" + Pattern.quote(word) + "\\b.*"));
    }


    static class MyRequest {
        private String postContent;

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

    }
}
