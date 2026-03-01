package com.myblog.contract.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateArticleRequestDTO {
    @NotBlank(message = "Title must not be blank")
    @Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @JsonProperty("content")
    private String content;

    @NotBlank(message = "Slug must not be blank")
    @Size(min = 3, max = 100, message = "Slug must be between 3 and 100 characters")
    @JsonProperty("slug")
    private String slug;

    @JsonProperty("seriesId")
    private long seriesId;

    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
