package com.hussainabdelilah.javaserver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @GetMapping("/")
    public Post test() {
        Post post = new Post();
        post.setContent("This is my first post");
        post.setCreatedAt(new Date());
        post.setLastModified(new Date());
        post.setPicture("1212");
        post.setUserId("111");
        post.setStatus(true);
        return postsRepository.save(post);
    }


}
