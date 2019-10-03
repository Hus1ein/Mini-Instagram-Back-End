package com.hussainabdelilah.javaserver.controllers;

import com.hussainabdelilah.javaserver.models.User;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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

    @GetMapping("/users/search")
    public ResponseEntity search(@AuthenticationPrincipal Principal principal, @RequestParam String username) {
        return new ResponseEntity<>(userService.search(principal.getName(), username), HttpStatus.OK);
    }

    @GetMapping("users/follow")
    public ResponseEntity follow(@AuthenticationPrincipal Principal principal, @RequestParam String followingId) {
        userService.follow(principal.getName(), followingId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity userData(@PathVariable String id) {
        return new ResponseEntity<>(userService.userData(id), HttpStatus.OK);
    }

    @PutMapping("users/")
    public ResponseEntity update(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

}
