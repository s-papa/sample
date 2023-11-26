package com.sample.http;

import com.sample.dto.BlogSearchRequest;
import com.sample.dto.BlogSearchResponse;

public interface BlogRequester {
    BlogSearchResponse search(BlogSearchRequest blogSearchRequest);
}
