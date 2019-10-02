package com.hussainabdelilah.javaserver.controllers;


import com.hussainabdelilah.javaserver.services.api.PostsService;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PostsService postsService;
    @Autowired
    private UserService userService;

    @PostMapping("/images")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity uploadImage(@AuthenticationPrincipal Principal principal, @RequestParam(value = "upload") MultipartFile image, @RequestParam String type) {
        String imageId = postsService.uploadImage(image, principal.getName(), type);

        Map<String, String> result = new HashMap<>();
        result.put("id", imageId);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/images/public/{id}")
    public ResponseEntity getImage(@PathVariable("id") String id, @RequestParam String token) {
        byte[] image = postsService.getFile(id, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
