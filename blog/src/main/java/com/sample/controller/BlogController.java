package com.sample.controller;

import com.sample.constant.BlogType;
import com.sample.constant.Sort;
import com.sample.dto.BlogSearchRequest;
import com.sample.dto.BlogSearchResponse;
import com.sample.dto.HotKeyword;
import com.sample.service.BlogSearchService;
import com.sample.service.HotKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
@ResponseBody
@AllArgsConstructor
public class BlogController {
    private final BlogSearchService blogSearchService;
    private final HotKeywordService hotKeywordService;

    @GetMapping("/search")
    @Operation(responses = {@ApiResponse(responseCode = "400", description = "Invalid Parameter")})
    public BlogSearchResponse search(@RequestParam(defaultValue = "KAKAO") BlogType blogType, @RequestParam String query,
                                     @RequestParam(defaultValue = "ACCURACY") Sort sort, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        BlogSearchRequest request = new BlogSearchRequest(blogType, query, sort, size, page);
        return blogSearchService.search(request);
    }

    @GetMapping("/hot-keywords")
    public List<HotKeyword> getHotKeywords() {
        return hotKeywordService.findHotKeywords();
    }
}