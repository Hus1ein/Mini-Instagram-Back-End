package com.hussainabdelilah.javaserver.repositories;

import com.hussainabdelilah.javaserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    List<User> findByUsernameLikeIgnoreCase(String username);

}
