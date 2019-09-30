package com.hussainabdelilah.javaserver.controllers;

import com.hussainabdelilah.javaserver.models.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(HttpServletResponse response) throws IOException {

        ClassPathResource imgFile = new ClassPathResource("images/sid.jpg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @PostMapping("/images")
    public ResponseEntity uploadImage(@RequestParam(value = "upload") MultipartFile image) {

        String fileName = image.getOriginalFilename();
        long fileSize = image.getSize();

        /*if (fileName == null || fileSize > 102400) {  //If file size is more than 100KB
            throw new BadInputException("Bad input");
        }*/

        /*String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!extension.equals("png") && !extension.equals("jpeg") && !extension.equals("jpg")) {
            throw new BadInputException("Bad input, You can use just png, jpg and jpeg images");
        }*/



        File createdImage = new File();
        //createdImage.setUserId(usersService.getLoggedUser().get("id"));
        createdImage.setCreatedAt(new Date());
        //filesRepository.save(createdImage);
        //return createdImage.getId();

        Map<String, String> result = new HashMap<>();
        result.put("id", createdImage.getId());

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
