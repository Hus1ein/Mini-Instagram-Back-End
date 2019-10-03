package com.hussainabdelilah.javaserver.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class User {

    @Id
    private String id;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String name;
    private String about;
    private String photo;
    private List<String> followers;
    private List<String> following;


}
