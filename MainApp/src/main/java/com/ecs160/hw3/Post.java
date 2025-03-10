package com.ecs160.hw3;

import java.util.List;
import java.util.ArrayList;

public class Post {
    private String uri;
    private String postDate;
    private String postContent;
    private int replyCount;
    private int likeCount;
    private List<Post> replies;

    public Post(String uri, String postDate, String postContent, int replyCount, int likeCount) {
        this.uri = uri;
        this.postDate = postDate;
        this.postContent = postContent;
        this.replyCount = replyCount;
        this.likeCount = likeCount;
        this.replies = new ArrayList<>();
    }

    public Post() {
        this.uri = "";
        this.postDate = "";
        this.postContent = "";
        this.replyCount = 0;
        this.replies = new ArrayList<>();
    }


    public String getUri () {
        return uri;
    }

    public void setUri (String cid) { this.uri = cid; }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<Post> getReplies() {
        return replies;
    }

    public void setReplies(List<Post> replies) {
        this.replies = replies;
    }

    public void addReply(Post reply) {
        if (reply != null) {
            this.replies.add(reply);
        }
    }

    // Override toString() for debugging
    @Override
    public String toString() {
        return "Post{" +
                "uri='" + uri + '\'' +
                ", postDate='" + postDate + '\'' +
                ", postContent='" + postContent + '\'' +
                ", replyCount=" + replyCount +
                ", replies=" + replies.size() + " replies" +
                '}';
    }
}