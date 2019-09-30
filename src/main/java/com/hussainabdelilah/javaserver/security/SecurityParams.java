package com.hussainabdelilah.javaserver.security;

public interface SecurityParams {

    String HEADER_KEY_NAME= "Authorization";
    String SECRET= "secret";
    long EXPIRATION = 100 * 24 * 3600 * 1000;
    String HEADER_TOKEN_PREFIX= "Bearer ";

}
