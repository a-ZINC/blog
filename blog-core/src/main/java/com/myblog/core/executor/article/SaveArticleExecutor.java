package com.myblog.core.executor.article;

import com.google.inject.Inject;
import com.myblog.common.exception.BlogException;
import com.myblog.core.bo.ArticleBO;
import com.myblog.core.context.ActionContext;
import com.myblog.core.executor.Executor;
import com.myblog.core.processor.ActionType;
import com.myblog.db.dao.ArticleDAO;
import com.myblog.db.entity.ArticleEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveArticleExecutor implements Executor {
    private final ArticleDAO articleDAO;

    @Inject
    public SaveArticleExecutor(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public void execute(ActionContext context) throws BlogException {
        ArticleBO article = context.get("input", ArticleBO.class);

        log.debug("Saving article slug={}", article.getSlug());

        ArticleEntity articleEntity = ArticleEntity.builder()
                        .title(article.getTitle())
                        .slug(article.getSlug())
                        .content(article.getContent())
                        .seriesId(article.getSeriesId())
                        .isPrivate(article.isPrivate())
                        .build();

        ArticleEntity saved = articleDAO.save(articleEntity);

        ArticleBO newArticle = ArticleBO.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .slug(saved.getSlug())
                .content(saved.getContent())
                .seriesId(saved.getSeriesId())
                .isPrivate(saved.isPrivate())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();

        context.set("savedArticle", newArticle);
        log.info("Article saved successfully: id={}, slug={}", newArticle.getId(), newArticle.getSlug());
    }
}
