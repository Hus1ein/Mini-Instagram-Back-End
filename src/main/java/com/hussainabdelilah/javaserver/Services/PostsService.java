package com.hussainabdelilah.javaserver.Services;


import com.hussainabdelilah.javaserver.Models.Post;
import com.hussainabdelilah.javaserver.Repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;

    public List<Post> getAllPosts() {
        //get logged userId
        String loggedUserId = "12";
        return postsRepository.findAllByUserIdAndStatusOrderByCreatedAtDesc(loggedUserId, true);
    }

    public Post getPostById(String postId) {
        String loggedUSerId = "12";
        return postsRepository.findByIdAndUserIdAndStatus(postId, loggedUSerId, true);
    }

    public Post createNewPost(Post post) {
        String loggedUserId = "12";
        post.setId(null);
        post.setUserId(loggedUserId);
        post.setCreatedAt(new Date());
        post.setLastModified(new Date());
        post.setLikedBy(new ArrayList<>());
        post.setStatus(true);
        return postsRepository.insert(post);
    }

    public void deletePost(String postId) {
        Post post = getPostById(postId);
        post.setStatus(false);
        this.postsRepository.save(post);
    }



}
