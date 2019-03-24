package com.hussainabdelilah.javaserver.Models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Comment {

    @Id
    private String id;
    private String userId;
    private String postId;
    private String content;
    private Date createdAt;
    private List<String> likedBy;

    public Comment() {}

    public Comment(String id, String userId, String postId, String content, Date createdAt, List<String> likedBy) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.likedBy = likedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

}
