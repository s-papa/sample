package com.sample.exception;

public class BlogSearchInvalidParamsException extends RuntimeException {
    public BlogSearchInvalidParamsException(String message) {
        super(message);
    }
}
