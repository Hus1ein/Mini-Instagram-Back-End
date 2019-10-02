package com.hussainabdelilah.javaserver.services.impl;

import com.hussainabdelilah.javaserver.AmazonClient;
import com.hussainabdelilah.javaserver.models.File;
import com.hussainabdelilah.javaserver.models.Post;
import com.hussainabdelilah.javaserver.models.User;
import com.hussainabdelilah.javaserver.repositories.FileRepository;
import com.hussainabdelilah.javaserver.repositories.PostsRepository;
import com.hussainabdelilah.javaserver.repositories.UserRepository;
import com.hussainabdelilah.javaserver.services.api.PostsService;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PostServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private AmazonClient amazonClient;
    @Autowired
    private UserService userService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Map<String, Object>> getAllPosts(String username) {
        List<Map<String, Object>> postsAsMap = new ArrayList<>();

        User user = userService.findByUsername(username);
        List<String> userFollowing = user.getFollowing();
        userFollowing.add(user.getId());

        List<Post> posts = postsRepository.findAllByUserIdInOrderByCreatedAtDesc(userFollowing);

        for (Post post : posts) {

            Map<String, String> userAsMap = new HashMap<>();
            User postUser = userRepository.findById(post.getUserId()).get();
            userAsMap.put("id", postUser.getId());
            userAsMap.put("name", postUser.getName());
            userAsMap.put("photo", postUser.getPhoto());

            Map<String, Object> postAsMap = new HashMap<>();
            postAsMap.put("id", post.getId());
            postAsMap.put("content", post.getContent());
            postAsMap.put("picture", post.getPicture());
            postAsMap.put("lastModified", post.getLastModified());
            postAsMap.put("user", userAsMap);
            postAsMap.put("likedBy", post.getLikedBy());

            postsAsMap.add(postAsMap);
        }

        return postsAsMap;

    }

    @Override
    public List<Map<String, Object>> getAllPostsByUserId(String userId) {
        List<Map<String, Object>> postsAsMap = new ArrayList<>();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User is not exist"));

        List<Post> posts = postsRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());

        for (Post post : posts) {

            Map<String, String> userAsMap = new HashMap<>();
            userAsMap.put("id", user.getId());
            userAsMap.put("name", user.getName());
            userAsMap.put("photo", user.getPhoto());

            Map<String, Object> postAsMap = new HashMap<>();
            postAsMap.put("id", post.getId());
            postAsMap.put("content", post.getContent());
            postAsMap.put("picture", post.getPicture());
            postAsMap.put("lastModified", post.getLastModified());
            postAsMap.put("user", userAsMap);
            postAsMap.put("likedBy", post.getLikedBy());

            postsAsMap.add(postAsMap);
        }

        return postsAsMap;
    }

    @Override
    public Post getPostById(String postId) {
        String loggedUSerId = "12";
        return postsRepository.findByIdAndUserIdAndStatus(postId, loggedUSerId, true);
    }

    @Override
    public Post create(Post post, String username) {
        User user = userService.findByUsername(username);
        post.setId(null);
        post.setUserId(user.getId());
        post.setContent(post.getContent());
        post.setPicture(post.getPicture());
        post.setCreatedAt(new Date());
        post.setLastModified(new Date());
        post.setLikedBy(new ArrayList<>());
        post.setStatus(true);
        return postsRepository.insert(post);
    }

    @Override
    public void deletePost(String postId) {
        Post post = getPostById(postId);
        post.setStatus(false);
        this.postsRepository.save(post);
    }

    @Override
    public String uploadImage(MultipartFile file, String username, String type) {

        User user = userService.findByUsername(username);

        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();

        /*if (fileName == null || fileSize > 102400) {  //If file size is more than 100KB
            throw new BadInputException("Bad input");
        }*/

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!extension.equals("png") && !extension.equals("jpeg") && !extension.equals("jpg")) {
            throw new RuntimeException("Bad input, You can use just png, jpg and jpeg images");
        }

        String imageKey = amazonClient.uploadFile(file, extension);
        if (imageKey == null) {
            throw new RuntimeException("Bad input");
        }

        File createdImage = new File();
        createdImage.setKey(imageKey);
        createdImage.setUserId(user.getId());
        createdImage.setCreatedAt(new Date());
        createdImage.setType(type);
        fileRepository.save(createdImage);
        return createdImage.getId();

    }

    @Override
    public byte[] getFile(String id, String token) {

        User user = userService.resolveToken(token);

        File file = fileRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bad input")
        );

        return amazonClient.getImage(file.getKey());

    }


}
