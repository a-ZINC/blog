package com.myblog.service.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.myblog.core.executor.Executor;
import com.myblog.core.executor.article.NotifyAnalyticsExecutor;
import com.myblog.core.executor.article.SaveArticleExecutor;
import com.myblog.core.executor.article.ValidateArticleExecutor;
import com.myblog.core.processor.ActionProcessor;
import com.myblog.core.processor.ActionType;
import com.myblog.service.BlogConfiguration;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BlogModule extends AbstractModule {
    private final BlogConfiguration configuration;
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Map<ActionType, List<Executor>> provideExecutorMap(
            ValidateArticleExecutor validateArticleExecutor,
            SaveArticleExecutor saveArticleExecutor,
            NotifyAnalyticsExecutor notifyAnalyticsExecutor
    ) {
        return Map.of(
                ActionType.CREATE_ARTICLE, List.of(validateArticleExecutor, saveArticleExecutor, notifyAnalyticsExecutor)
        );
    }

    @Provides
    @Singleton
    public ActionProcessor provideActionPRocessor(Map<ActionType, List<Executor>> executorMap) {
        return new ActionProcessor(executorMap);
    }
}
