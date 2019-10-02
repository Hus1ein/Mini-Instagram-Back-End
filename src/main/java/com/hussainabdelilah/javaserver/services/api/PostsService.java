package com.hussainabdelilah.javaserver.services.api;

import com.hussainabdelilah.javaserver.models.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PostsService {

    List<Map<String, Object>> getAllPosts(String username);
    List<Map<String, Object>> getAllPostsByUserId(String userId);

    Post getPostById(String postId);

    Post create(Post post, String username);

    void deletePost(String postId);

    String uploadImage(MultipartFile file, String username, String type);

    public byte[] getFile(String id, String token);

}
