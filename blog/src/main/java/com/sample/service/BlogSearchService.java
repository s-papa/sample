package com.sample.service;

import com.sample.constant.BlogType;
import com.sample.dto.BlogSearchRequest;
import com.sample.dto.BlogSearchResponse;
import com.sample.exception.BlogSearchServerException;
import com.sample.http.BlogRequesterFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogSearchService {

    private final HotKeywordService hotKeywordService;
    private final BlogRequesterFinder blogRequesterFinder;

    public BlogSearchResponse search(BlogSearchRequest blogSearchRequest) {
        blogSearchRequest.validate();
        BlogSearchResponse response;
        try {
            response = blogRequesterFinder.find(blogSearchRequest.getBlogType()).search(blogSearchRequest);
        } catch (BlogSearchServerException e) {
            BlogType secondType = blogSearchRequest.getBlogType() == BlogType.KAKAO ? BlogType.NAVER : BlogType.KAKAO;
            response = blogRequesterFinder.find(secondType).search(blogSearchRequest);
        }

        if (blogSearchRequest.getPage() == 1) {
            hotKeywordService.increaseCountByKeyword(blogSearchRequest.getKeyword());
        }
        return response;
    }
}