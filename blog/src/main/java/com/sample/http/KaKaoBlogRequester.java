package com.sample.http;

import com.sample.constant.Sort;
import com.sample.dto.Blog;
import com.sample.dto.BlogSearchRequest;
import com.sample.dto.BlogSearchResponse;
import com.sample.dto.KakaoBlogResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@AllArgsConstructor
public class KaKaoBlogRequester implements BlogRequester {

    private final RestTemplate restTemplate;

    @Override
    public BlogSearchResponse search(BlogSearchRequest blogSearchRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 55ab449367dee4a2e10a631966f04365");

        HttpEntity<KakaoBlogResponse> httpEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/search/blog")
                .queryParam("query", blogSearchRequest.getKeyword())
                .queryParam("sort", getStringSort(blogSearchRequest.getSort()))
                .queryParam("page", blogSearchRequest.getPage())
                .queryParam("size", blogSearchRequest.getSize())
                .encode()
                .toUriString();

        ResponseEntity<KakaoBlogResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, KakaoBlogResponse.class);
        KakaoBlogResponse response =  responseEntity.getBody();
        return convert(blogSearchRequest, response);
    }

    private String getStringSort(Sort sort){
        return switch (sort){
            case RECENCY -> "recency";
            default -> "accuracy";
        };
    }
    private BlogSearchResponse convert(BlogSearchRequest request, KakaoBlogResponse response){
        BlogSearchResponse blogSearchResponse = new BlogSearchResponse();
        blogSearchResponse.setPageInfo(request.getPage(), request.getSize(), response.getMeta().getPageableCount());
        List<Blog> blogs = response.getDocuments().stream().map(KakaoBlogResponse.Document::toBlog).toList();
        blogSearchResponse.setBlogs(blogs);
        return blogSearchResponse;
    }
}