package com.myblog.core.bo;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ArticleBO {

    private long id;
    private String title;
    private String content;
    private String slug;
    private long seriesId;
    private boolean isPrivate;
    private Instant createdAt;
    private Instant updatedAt;
}
