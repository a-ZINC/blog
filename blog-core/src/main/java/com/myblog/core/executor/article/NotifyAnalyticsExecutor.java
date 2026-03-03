package com.myblog.core.executor.article;

import com.google.inject.Inject;
import com.myblog.common.exception.BlogException;
import com.myblog.core.bo.ArticleBO;
import com.myblog.core.context.ActionContext;
import com.myblog.core.executor.Executor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NotifyAnalyticsExecutor implements Executor {

    @Inject
    public NotifyAnalyticsExecutor() {}

    @Override
    public void execute(ActionContext context) throws BlogException {
        ArticleBO savedArticle = context.get("savedArticle", ArticleBO.class);

        try {
            log.info("Notifying analytics for articleId={} slug={}",
                    savedArticle.getId(), savedArticle.getSlug());

        } catch (Exception e) {
            log.warn("Analytics notification failed for slug={} — continuing anyway",
                    savedArticle.getSlug(), e);
        }
    }
}
