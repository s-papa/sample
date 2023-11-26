package com.sample.handler;

import com.sample.exception.BlogSearchClientException;
import com.sample.exception.BlogSearchInvalidParamsException;
import com.sample.exception.BlogSearchServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogSearchClientException.class)
    public ResponseEntity<String> handleBlogSearchClientException(BlogSearchClientException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlogSearchServerException.class)
    public ResponseEntity<String> handleBlogSearchServerException(BlogSearchServerException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BlogSearchInvalidParamsException.class)
    public ResponseEntity<String> handleNoContentException(BlogSearchInvalidParamsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

