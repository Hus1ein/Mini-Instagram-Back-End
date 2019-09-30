package com.hussainabdelilah.javaserver.repositories;

import com.hussainabdelilah.javaserver.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepository extends MongoRepository<Post, String> {

    List<Post> findAllByUserIdAndStatusOrderByCreatedAtDesc(String userId, boolean status);
    Post findByIdAndUserIdAndStatus(String id, String userId, boolean status);
}
