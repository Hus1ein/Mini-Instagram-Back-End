package com.hussainabdelilah.javaserver.services.impl;

import com.hussainabdelilah.javaserver.models.User;
import com.hussainabdelilah.javaserver.repositories.UserRepository;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User register(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("This email is already exists");
        }

        user.setId(UUID.randomUUID().toString());
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
