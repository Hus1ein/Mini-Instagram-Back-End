package com.hussainabdelilah.javaserver.services.api;

import com.hussainabdelilah.javaserver.models.Post;
import java.util.List;

public interface PostsService {

    List<Post> getAllPosts();

    Post getPostById(String postId);

    Post createNewPost(Post post);

    void deletePost(String postId);

}
