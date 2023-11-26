package com.sample.http;

import com.sample.constant.BlogType;
import com.sample.constant.Sort;
import com.sample.dto.Blog;
import com.sample.dto.BlogSearchRequest;
import com.sample.dto.BlogSearchResponse;
import com.sample.exception.BlogSearchClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@SpringBootTest
class NaverBlogRequesterTest {
    @Autowired
    private NaverBlogRequester requester;

    @Test
    public void search() {
        BlogSearchRequest request = new BlogSearchRequest(BlogType.NAVER, "테스트", Sort.ACCURACY, 10, 1);
        BlogSearchResponse response = requester.search(request);
        assertFalse(response.getBlogs().isEmpty());
    }

    @Test
    public void search_paging() {
        int size = 5;
        int page = 2;
        BlogSearchRequest request1 = new BlogSearchRequest(BlogType.NAVER, "하늘", Sort.ACCURACY, size, page);
        BlogSearchResponse response1 = requester.search(request1);

        BlogSearchRequest request2 = new BlogSearchRequest(BlogType.NAVER, "하늘", Sort.ACCURACY, size * page, 1);
        BlogSearchResponse response2 = requester.search(request2);

        assumingThat(response1.getBlogs().size() == size && response2.getBlogs().size() == size * page, () -> {
            Blog lastBlog1 = response1.getBlogs().get(response1.getBlogs().size() - 1);
            Blog lastBlog2 = response2.getBlogs().get(response2.getBlogs().size() - 1);
            assertEquals(lastBlog1.getContents(), lastBlog2.getContents());
        });
    }

    @Test
    public void search_invalid_params() {
        assertThrows(BlogSearchClientException.class, () -> {
            BlogSearchRequest request = new BlogSearchRequest(BlogType.NAVER, "___", Sort.ACCURACY, 10, 1);
            requester.search(request);
        });

        assertThrows(BlogSearchClientException.class, () -> {
            BlogSearchRequest request = new BlogSearchRequest(BlogType.NAVER, "___", Sort.ACCURACY, 10, Integer.MAX_VALUE);
            requester.search(request);
        });


    }
}