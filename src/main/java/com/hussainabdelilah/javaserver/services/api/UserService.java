package com.hussainabdelilah.javaserver.services.api;

import com.hussainabdelilah.javaserver.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User register(User user);

    User findByUsername(String username);

    List<Map<String, Object>> search(String currentUsername, String username);

    void follow(String username, String followingId);

    Map<String, Object> userData(String id);

    User resolveToken(String token);

    void update(User user);

}
