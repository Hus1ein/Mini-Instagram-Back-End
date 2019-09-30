package com.hussainabdelilah.javaserver.services.api;

import com.hussainabdelilah.javaserver.models.User;

public interface UserService {

    User register(User user);

    User findByUsername(String username);

}
