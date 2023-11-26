package com.sample.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverBlogResponse {
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Item {
        private String title;
        private String description;
        private String link;
        private String bloggername;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        private LocalDate postdate;

        public Blog toBlog() {
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setContents(description);
            blog.setUrl(link);
            blog.setBlogName(bloggername);
            blog.setThumbnail(null);
            blog.setDateTime(postdate.atStartOfDay());
            return blog;
        }
    }
}