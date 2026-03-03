package com.myblog.core.executor.article;

import com.myblog.common.exception.BlogException;
import com.myblog.core.bo.ArticleBO;
import com.myblog.core.context.ActionContext;
import com.myblog.core.executor.Executor;
import com.myblog.core.processor.ActionType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveArticleExecutor implements Executor {

    public SaveArticleExecutor() {}

    @Override
    public void execute(ActionContext context) throws BlogException {
        ArticleBO article = context.get("input", ArticleBO.class);

        log.debug("Saving article slug={}", article.getSlug());

        ArticleBO newArticle = ArticleBO.builder()
                .id(123L)
                .slug(article.getSlug())
                .title(article.getTitle())
                .content(article.getContent())
                .build();

        context.set("savedArticle", newArticle);
    }
}
