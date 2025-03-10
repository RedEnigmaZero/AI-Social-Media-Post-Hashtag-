package com.ecs160.hw3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    public List<Post> parseJson(String filePath) {
        List<Post> posts = new ArrayList<>();
        try {
            // Parse JSON file
            JsonElement element = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(JsonParser.class.getClassLoader().getResourceAsStream(filePath))));
            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                JsonArray feedArray = jsonObject.get("feed").getAsJsonArray();

                for (JsonElement feedElement : feedArray) {
                    if (feedElement.getAsJsonObject().has("thread")) {
                        JsonObject threadObject = feedElement.getAsJsonObject().get("thread").getAsJsonObject();
                        Post post = parsePost(threadObject);
                        posts.add(post);
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    private Post parsePost(JsonObject threadObject) {
        Post post = new Post();
        JsonObject postObject = threadObject.get("post").getAsJsonObject();
        JsonObject authorObject = postObject.get("author").getAsJsonObject();
        JsonObject recordObject = postObject.get("record").getAsJsonObject();

        // Parse post fields
        post.setUri(postObject.get("uri").getAsString());
        post.setPostDate(authorObject.get("createdAt").getAsString());
        post.setReplyCount(postObject.get("replyCount").getAsInt());
        post.setLikeCount(postObject.get("likeCount").getAsInt());
        post.setPostContent(recordObject.get("text").getAsString());

        // Parse replies (if any)
        if (threadObject.has("replies")) {

            JsonArray repliesArray = threadObject.get("replies").getAsJsonArray();
            List<Post> replies = new ArrayList<>();
            for (JsonElement replyElement : repliesArray) {
                replies.add(parsePost(replyElement.getAsJsonObject()));
            }
            post.setReplies(replies);
        }

        return post;
    }
}


