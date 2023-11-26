package com.sample.exception;

public class BlogSearchServerException extends RuntimeException {
    public BlogSearchServerException(String message) {
        super(message);
    }
}
