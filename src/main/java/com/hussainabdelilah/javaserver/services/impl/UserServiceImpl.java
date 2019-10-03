package com.hussainabdelilah.javaserver.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hussainabdelilah.javaserver.models.User;
import com.hussainabdelilah.javaserver.repositories.PostsRepository;
import com.hussainabdelilah.javaserver.repositories.UserRepository;
import com.hussainabdelilah.javaserver.security.SecurityParams;
import com.hussainabdelilah.javaserver.services.api.PostsService;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PostsRepository postsRepository;

    @Override
    public User register(User user) {

        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            throw new RuntimeException("This email is already exists");
        }

        user.setId(UUID.randomUUID().toString());
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setName(user.getName());
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());

        return userRepository.save(user);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Map<String, Object>> search(String currentUsername, String username) {
        User currentUser = findByUsername(currentUsername);
        List<User> users = userRepository.findByUsernameLikeIgnoreCase(username);
        List<Map<String, Object>> usersAsMap = new ArrayList<>();
        for (User user : users) {
            boolean canFollow = true;
            for (int i=0; i < user.getFollowers().size(); i++) {
                if (user.getFollowers().get(i).equals(currentUser.getId())) {
                    canFollow = false;
                }
            }
            if (currentUser.getId().equals(user.getId())) {
                canFollow = false;
            }

            Map<String, Object> userAsMap = new HashMap<>();
            userAsMap.put("id", user.getId());
            userAsMap.put("username", user.getUsername());
            userAsMap.put("photo", user.getPhoto());
            userAsMap.put("canFollow", canFollow);

            usersAsMap.add(userAsMap);
        }

        return usersAsMap;
    }

    @Override
    public void follow(String username, String followingId) {

        User currentUser = userRepository.findByUsername(username);
        User followingUser = userRepository.findById(followingId).orElseThrow(
                () -> new RuntimeException("User not exist"));

        List<String> currentUserFollowing = currentUser.getFollowing();
        currentUserFollowing.add(followingUser.getId());
        currentUser.setFollowing(currentUserFollowing);

        List<String> followingUserFollowers = followingUser.getFollowers();
        followingUserFollowers.add(currentUser.getId());
        followingUser.setFollowers(followingUserFollowers);

        userRepository.save(currentUser);
        userRepository.save(followingUser);

    }

    @Override
    public Map<String, Object> userData(String id) {
        Map<String, Object> userData = new HashMap<>();

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User is not exist"));

        userData.put("username", user.getUsername());
        userData.put("name", user.getName());
        userData.put("about", user.getAbout());
        userData.put("photo", user.getPhoto());
        userData.put("followingNumber", user.getFollowing().size());
        userData.put("followersNumber", user.getFollowers().size());
        userData.put("postsNumber", postsRepository.countAllByUserId(id));

        return userData;
    }

    @Override
    public User resolveToken(String token) {
        token = "Bearer " + token;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(token.substring(SecurityParams.HEADER_TOKEN_PREFIX.length()));
        String email = decodedJWT.getSubject();

        return userRepository.findByUsername(email);

    }

    @Override
    public void update(User user) {
        User currentUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User is not exist"));

        if (user.getName() != null) {
            currentUser.setName(user.getName());
        }

        if (user.getAbout() != null) {
            currentUser.setAbout(user.getAbout());
        }

        if (user.getPhoto() != null) {
            currentUser.setPhoto(user.getPhoto());
        }

        userRepository.save(currentUser);
    }

}
