package com.sample.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BlogSearchResponse {
    private int page;
    private int size;
    private int totalPage;

    public void setPageInfo(int page, int size, int totalCount){
        this.page = page;
        this.size = size;
        this.totalPage =  totalCount < size ? 1 : (int) Math.ceil((double) totalCount / size);
    }

    private List<Blog> blogs;
}