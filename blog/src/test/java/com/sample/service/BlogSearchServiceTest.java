package com.sample.service;

import com.sample.constant.BlogType;
import com.sample.constant.Sort;
import com.sample.dto.BlogSearchRequest;
import com.sample.dto.BlogSearchResponse;
import com.sample.exception.BlogSearchInvalidParamsException;
import com.sample.model.HotKeywordEntity;
import com.sample.repository.HotKeywordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BlogSearchServiceTest {
    @Autowired
    private BlogSearchService blogSearchService;

    @Autowired
    private HotKeywordRepository hotKeywordRepository;

    @Test
    public void search_invalid_params() {
        BlogSearchRequest request = makeMockBlogSearchRequest();
        request.setKeyword("  ");
        assertThrows(BlogSearchInvalidParamsException.class, () -> {
            blogSearchService.search(request);
        });
    }

    @Test
    public void search() {
        BlogSearchRequest request = makeMockBlogSearchRequest();
        BlogSearchResponse response = blogSearchService.search(request);
        assertFalse(response.getBlogs().isEmpty());

        Optional<HotKeywordEntity> entity = hotKeywordRepository.findByKeyword(request.getKeyword());
        assertTrue(entity.isPresent());
        assertEquals(1, entity.get().getCount());


        blogSearchService.search(request);
        Optional<HotKeywordEntity> entity2 = hotKeywordRepository.findByKeyword(request.getKeyword());
        assertEquals(2, entity2.get().getCount());
    }

    @Test
    public void search_page1_increase() {
        BlogSearchRequest request = makeMockBlogSearchRequest();
        BlogSearchResponse response = blogSearchService.search(request);
        assertFalse(response.getBlogs().isEmpty());

        Optional<HotKeywordEntity> entity = hotKeywordRepository.findByKeyword(request.getKeyword());
        assertTrue(entity.isPresent());
        assertEquals(1, entity.get().getCount());

        blogSearchService.search(request);
        Optional<HotKeywordEntity> entity2 = hotKeywordRepository.findByKeyword(request.getKeyword());
        assertEquals(2, entity2.get().getCount());
    }

    @Test
    public void search_page2_no_increase() {
        BlogSearchRequest request = makeMockBlogSearchRequest();
        BlogSearchResponse response = blogSearchService.search(request);
        assertFalse(response.getBlogs().isEmpty());

        Optional<HotKeywordEntity> entity = hotKeywordRepository.findByKeyword(request.getKeyword());
        assertTrue(entity.isPresent());
        assertEquals(1, entity.get().getCount());

        request.setPage(2);
        blogSearchService.search(request);
        Optional<HotKeywordEntity> entity2 = hotKeywordRepository.findByKeyword(request.getKeyword());
        assertEquals(1, entity2.get().getCount());
    }

    private BlogSearchRequest makeMockBlogSearchRequest() {
        return new BlogSearchRequest(BlogType.KAKAO, "하늘", Sort.RECENCY, 10, 1);
    }
}
