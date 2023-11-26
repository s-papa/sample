package com.sample.http;

import com.sample.constant.BlogType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogRequesterFinder {
    private final KaKaoBlogRequester kaKaoBlogRequester;
    private final NaverBlogRequester naverBlogRequester;

    public BlogRequester find(BlogType blogType){
        return switch (blogType){
            case KAKAO -> kaKaoBlogRequester;
            case NAVER -> naverBlogRequester;
        };
    }
}
