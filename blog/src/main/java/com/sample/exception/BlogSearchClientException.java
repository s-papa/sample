package com.sample.exception;

public class BlogSearchClientException extends RuntimeException {
    public BlogSearchClientException(String message) {
        super(message);
    }
}
