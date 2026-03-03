package com.myblog.core.executor.article;

import com.google.inject.Inject;
import com.myblog.common.exception.BlogException;
import com.myblog.common.exception.ErrorCode;
import com.myblog.core.bo.ArticleBO;
import com.myblog.core.context.ActionContext;
import com.myblog.core.executor.Executor;
import com.myblog.db.dao.ArticleDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateArticleExecutor implements Executor {
    private final ArticleDAO articleDAO;

    @Inject
    public ValidateArticleExecutor(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public void execute(ActionContext context) throws BlogException {
        ArticleBO articleBO = context.get("input", ArticleBO.class);
        if (articleBO == null) {
            throw new BlogException(ErrorCode.VALIDATION_ERROR, "Article data is required");
        }
        log.info("Validating article: {}", articleBO);

        if (!articleBO.getSlug().matches("^[a-z0-9]+(?:-[a-z0-9]+)*$")) {
            throw new BlogException(ErrorCode.VALIDATION_ERROR, "Slug must be lowercase letters, numbers, and hyphens only");
        }

        if (articleDAO.findBySlug(articleBO.getSlug()).isPresent()) {
            throw new BlogException(ErrorCode.DUPLICATE_SLUG, "Slug already exists");
        }
    }
}
