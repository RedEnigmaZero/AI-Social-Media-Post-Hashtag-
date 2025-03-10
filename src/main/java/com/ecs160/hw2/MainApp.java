package com.ecs160.hw2;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.ecs160.hw2.Parser;

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

        for (Post post : topPost) {

        }

    }
}
