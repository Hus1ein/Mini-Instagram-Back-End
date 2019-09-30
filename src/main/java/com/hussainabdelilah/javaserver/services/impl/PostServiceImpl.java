package com.hussainabdelilah.javaserver.services.impl;

import com.hussainabdelilah.javaserver.models.Post;
import com.hussainabdelilah.javaserver.repositories.PostsRepository;
import com.hussainabdelilah.javaserver.services.api.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public List<Post> getAllPosts() {
        //get logged userId
        String loggedUserId = "12";
        return postsRepository.findAllByUserIdAndStatusOrderByCreatedAtDesc(loggedUserId, true);
    }

    @Override
    public Post getPostById(String postId) {
        String loggedUSerId = "12";
        return postsRepository.findByIdAndUserIdAndStatus(postId, loggedUSerId, true);
    }

    @Override
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

    @Override
    public void deletePost(String postId) {
        Post post = getPostById(postId);
        post.setStatus(false);
        this.postsRepository.save(post);
    }


}
