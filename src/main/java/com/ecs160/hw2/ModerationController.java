package com.ecs160.hw2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ModerationController {
    private static final List<String> bannedWords = List.of("illegal","fraud", "scam",
            "exploit", "dox", "swatting", "hack", "crypto", "bots");
    @PostMapping("/moderate")
    public String moderate(@RequestBody MyRequest request) {
        // Moderation logic
        if (checkBannedWords(request.getPostContent())) {
            return "FAILED";
        } else {
            String hashtag = callHashtagService(request.getPostContent());
            return hashtag;
        }
    }

    private boolean checkBannedWords(String content) {
        String lowerCase = content.toLowerCase();
        return bannedWords.stream()
                .anyMatch(word -> lowerCase.matches(".*\\b" + word + "\\b.*"));
    }

    private String callHashtagService(String content) {
        try {
            // HTTP call to hashtag service
            return hashtag;
        } catch (Exception e) {
            return "#bskypost";
        }
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
