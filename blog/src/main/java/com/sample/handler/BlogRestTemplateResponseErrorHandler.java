package com.sample.handler;

import com.sample.exception.BlogSearchClientException;
import com.sample.exception.BlogSearchServerException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class BlogRestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw new BlogSearchClientException(response.getBody().toString());
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new BlogSearchServerException(response.getBody().toString());
        }
    }
}