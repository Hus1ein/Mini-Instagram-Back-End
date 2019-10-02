package com.hussainabdelilah.javaserver.repositories;

import com.hussainabdelilah.javaserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByUsernameLike(String username);

}
