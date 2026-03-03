package com.myblog.core.bo;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;


@Data
@Builder
public class SeriesBO {

    private long id;
    private String title;
    private String slug;
    private String description;
    private boolean isPrivate;
    private List<ArticleBO> articles;
    private Instant createdAt;
}
