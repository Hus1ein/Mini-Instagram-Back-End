package com.hussainabdelilah.javaserver.Models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Post {

    @Id
    private String id;
    private String userId;
    private String content;
    private String picture;
    private Date createdAt;
    private Date lastModified;
    private List<String> likedBy;
    private boolean status;

    public Post() {}

    public Post(String id, String userId, String content, String picture, Date createdAt, Date lastModified, List<String> likedBy, boolean status) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.picture = picture;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.likedBy = likedBy;
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
