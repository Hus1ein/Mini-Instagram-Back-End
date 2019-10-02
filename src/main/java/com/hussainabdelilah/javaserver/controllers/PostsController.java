package com.hussainabdelilah.javaserver.controllers;


import com.hussainabdelilah.javaserver.models.Post;
import com.hussainabdelilah.javaserver.services.api.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPosts(@AuthenticationPrincipal Principal principal) {
        return new ResponseEntity<>(postsService.getAllPosts(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllPostsByUserId(@PathVariable String id) {
        return new ResponseEntity<>(postsService.getAllPostsByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNewPost(@AuthenticationPrincipal Principal principal, @RequestBody Post post) {
        Post newPost = postsService.create(post, principal.getName());
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") String id) {
        this.postsService.deletePost(id);
        Map<String, String> message = new HashMap<>();
        message.put("message", "Deleted");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
