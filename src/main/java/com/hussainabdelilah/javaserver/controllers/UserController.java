package com.hussainabdelilah.javaserver.controllers;

import com.hussainabdelilah.javaserver.models.User;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user) {
        userService.register(user);
        Map<String, String> result = new HashMap<>();
        result.put("message", "created");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
