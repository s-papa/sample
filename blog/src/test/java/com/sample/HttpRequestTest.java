package com.sample;

import com.sample.dto.BlogSearchResponse;
import com.sample.dto.HotKeyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test() {
        for (int i = 0; i < 10; i++) {
            BlogSearchResponse response = restTemplate.getForObject("http://localhost:" + port + "/blog/search?query=하늘", BlogSearchResponse.class);
            assertFalse(response.getBlogs().isEmpty());
        }
        ResponseEntity<List<HotKeyword>> response = restTemplate.exchange(
                "http://localhost:" + port + "/blog/hot-keywords", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HotKeyword>>() {
                }
        );

        assertEquals("하늘", response.getBody().get(0).getKeyword());
        assertEquals(10, response.getBody().get(0).getCount());
    }
}