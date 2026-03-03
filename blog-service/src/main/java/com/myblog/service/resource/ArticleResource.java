package com.myblog.service.resource;


import com.google.inject.Inject;
import com.myblog.contract.request.CreateArticleRequestDTO;
import com.myblog.contract.response.ArticleResponseDTO;
import com.myblog.core.bo.ArticleBO;
import com.myblog.core.context.ActionContext;
import com.myblog.core.processor.ActionProcessor;
import com.myblog.core.processor.ActionType;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    private final ActionProcessor actionProcessor;

    @Inject
    public ArticleResource(ActionProcessor actionProcessor) {
        this.actionProcessor = actionProcessor;
    }

    @POST
    public Response createArticle(@Valid CreateArticleRequestDTO dto) {
        try {
            ArticleBO inputBO = ArticleBO.builder()
                    .title(dto.getTitle())
                    .slug(dto.getSlug())
                    .content(dto.getContent())
                    .seriesId(dto.getSeriesId())
                    .isPrivate(dto.isPrivate())
                    .build();

            ActionContext context = actionProcessor.process(ActionType.CREATE_ARTICLE, inputBO);

            ArticleBO articleBO = context.get("savedArticle", ArticleBO.class);

            ArticleResponseDTO responseDTO = ArticleResponseDTO.builder()
                    .id(articleBO.getId())
                    .slug(articleBO.getSlug())
                    .title(articleBO.getTitle())
                    .content(articleBO.getContent())
                    .seriesId(articleBO.getSeriesId())
                    .isPrivate(articleBO.isPrivate())
                    .createdAt(articleBO.getCreatedAt())
                    .updatedAt(articleBO.getUpdatedAt())
                    .build();
            return Response.status(Response.Status.CREATED)
                    .entity(responseDTO)
                    .build();
        } catch (Exception e) {
            log.error("Error creating article: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating article: " + e.getMessage())
                    .build();
        }
    }
}
