package com.myblog.contract.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateCommentRequestDTO {

    @NotBlank(message = "Content must not be blank")
    @JsonProperty("content")
    @Size(max = 1000, message = "Content must be less than 1000 characters")
    private String content;

    @JsonProperty("parentId")
    private long parentId;
}
