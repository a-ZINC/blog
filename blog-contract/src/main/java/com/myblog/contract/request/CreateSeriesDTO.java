package com.myblog.contract.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSeriesDTO {
    @Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
    @NotBlank(message = "Title must not be blank")
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("isPrivate")
    private boolean isPrivate = false;
}
