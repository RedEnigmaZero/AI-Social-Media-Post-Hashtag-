package com.ecs160.hw2;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        // Path to the JSON file
        String filePath = "input.json";

        // Parse the JSON file
        Parser jsonParser = new Parser();

        List<Post> posts = jsonParser.parseJson(filePath);

        System.out.println(posts.get(0));
    }
}
