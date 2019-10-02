package com.hussainabdelilah.javaserver.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String about;
    private String photo;
    private List<String> followers;
    private List<String> following;


}
