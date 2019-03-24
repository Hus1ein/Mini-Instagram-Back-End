package com.hussainabdelilah.javaserver.Controllers;


import com.hussainabdelilah.javaserver.Models.Post;
import com.hussainabdelilah.javaserver.Services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPosts() {
        List<Post> allPosts = this.postsService.getAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") String id) {
        Post post = this.postsService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNewPost(@RequestBody Post post) {
        Post newPost = this.postsService.createNewPost(post);
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
