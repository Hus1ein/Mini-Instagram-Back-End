package com.hussainabdelilah.javaserver.repositories;

import com.hussainabdelilah.javaserver.models.File;
import com.hussainabdelilah.javaserver.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {

    File findByIdAndUserId(String id, String userId);

}
