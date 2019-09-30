package com.hussainabdelilah.javaserver.repositories;

import com.hussainabdelilah.javaserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

}
