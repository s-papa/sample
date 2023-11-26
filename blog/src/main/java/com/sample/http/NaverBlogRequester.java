package com.sample.http;

import com.sample.constant.Sort;
import com.sample.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class NaverBlogRequester implements BlogRequester {
    final RestTemplate restTemplate;

    @Override
    public BlogSearchResponse search(BlogSearchRequest blogSearchRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "1Zuxd1zNzPcqySe0vJBi");
        headers.set("X-Naver-Client-Secret", "Gmy3085AUv");

        HttpEntity<KakaoBlogResponse> httpEntity = new HttpEntity<>(headers);
        String url = UriComponentsBuilder.fromHttpUrl("https://openapi.naver.com/v1/search/blog.json")
                .queryParam("query", blogSearchRequest.getKeyword())
                .queryParam("sort", getStringSort(blogSearchRequest.getSort()))
                .queryParam("start", (blogSearchRequest.getPage() - 1) * blogSearchRequest.getSize() + 1)
                .queryParam("display", blogSearchRequest.getSize())
                .encode()
                .toUriString();

        ResponseEntity<NaverBlogResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, NaverBlogResponse.class);
        System.out.println(log.isDebugEnabled());
        System.out.println(responseEntity.getBody());
        log.info(responseEntity.getBody().toString());

        return convert(blogSearchRequest, responseEntity.getBody());
    }

    private String getStringSort(Sort sort) {
        return switch (sort) {
            case RECENCY -> "date";
            default -> "sim";
        };
    }

    private BlogSearchResponse convert(BlogSearchRequest request, NaverBlogResponse response) {
        BlogSearchResponse blogSearchResponse = new BlogSearchResponse();
        blogSearchResponse.setPageInfo(request.getPage(), request.getSize(), response.getTotal());
        List<Blog> blogs = response.getItems().stream().map(NaverBlogResponse.Item::toBlog).toList();
        blogSearchResponse.setBlogs(blogs);
        return blogSearchResponse;
    }
}