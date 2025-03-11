package com.ecs160.hw3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.ecs160.hw3.Parser;

public class MainApp {
    public static void main(String[] args) {
        // Path to the JSON file
        String filePath = "input.json";

        // Parse the JSON file
        Parser jsonParser = new Parser();

        List<Post> posts = jsonParser.parseJson(filePath);

        List<Post> topPost = posts.stream()
                .sorted(Comparator.comparingInt(Post::getLikeCount).reversed())
                .limit(10)
                .toList();

        /*
        int n = 0;
        for (Post p: topPost) {
            System.out.println("Post: " + n + " Likes: " + p.getLikeCount());
            System.out.println(p.getPostContent());
            n++;
        }

         */


        processPost(posts.get(1));
        /*
        for (Post post : topPost) {
            processPost(post);
        }


        n = 0;
        for (Post p: topPost) {
            System.out.println("Post: " + n + " Likes: " + p.getLikeCount());
            System.out.println(p.getPostContent());
            n++;
        }

         */

    }

    private static void processPost(Post post) {
        String postContent = post.getPostContent();
        String postResult = callModServie(postContent);

        StringBuilder output = new StringBuilder();
        if ("FAILED".equals(postResult)) {
            output.append("[DELETED]\n");
        } else {
            output.append(postContent).append(" ").append(postResult).append("\n");
        }

        // Process replies (direct replies only)
        for (Post reply : post.getReplies()) {
            String replyResult = callModServie(reply.getPostContent());
            if ("FAILED".equals(replyResult)) {
                output.append("--> [DELETED]\n");
            } else {
                output.append("--> ").append(reply.getPostContent()).append(" ").append(replyResult).append("\n");
            }
        }

        System.out.println(output);
    }

    private static String callModServie(String content) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String jsonBody = String.format("{\"postContent\": \"%s\"}", content.replace("\"", "\\\""));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:30000/moderate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            return "FAILED";
        }
    }
}
