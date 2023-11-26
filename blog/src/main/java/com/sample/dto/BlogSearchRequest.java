package com.sample.dto;

import com.sample.constant.BlogType;
import com.sample.constant.Sort;
import com.sample.exception.BlogSearchInvalidParamsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BlogSearchRequest {
    private BlogType blogType;
    private String keyword;
    private Sort sort;
    private int size;
    private int page;

    public void validate() {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 1;
        } else if (size > 20) {
            size = 20;
        }

        keyword = keyword.trim();
        if (keyword.isEmpty()) {
            throw new BlogSearchInvalidParamsException("The length of a keyword must be greater than zero.");
        } else if (keyword.length() > 100) {
            throw new BlogSearchInvalidParamsException("The length of a keyword can't be longer than 100 characters.");
        }
    }
}
