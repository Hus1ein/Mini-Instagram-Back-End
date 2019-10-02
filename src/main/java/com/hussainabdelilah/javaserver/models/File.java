package com.hussainabdelilah.javaserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    private String id;
    private String key;
    private String userId;
    private Date createdAt;
    private String type; // profile or post

}