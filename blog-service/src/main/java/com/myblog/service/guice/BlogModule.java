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
import com.myblog.db.dao.ArticleDAO;
import com.myblog.db.dao.SeriesDAO;
import com.myblog.db.dao.UserDAO;
import com.myblog.service.BlogConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

public class BlogModule extends AbstractModule {
    private final BlogConfiguration configuration;
    private final HibernateBundle<BlogConfiguration> hibernateBundle;

    public BlogModule(BlogConfiguration configuration, HibernateBundle<BlogConfiguration> hibernateBundle) {
        this.configuration = configuration;
        this.hibernateBundle = hibernateBundle;

    }
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

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }

    @Provides
    @Singleton
    public ArticleDAO provideArticleDAO(SessionFactory sessionFactory) {
        return new ArticleDAO(sessionFactory);
    }

    @Provides
    @Singleton
    public SeriesDAO provideSeriesDAO(SessionFactory sessionFactory) {
        return new SeriesDAO(sessionFactory);
    }

    @Provides
    @Singleton
    public UserDAO provideUserDAO(SessionFactory sessionFactory) {
        return new UserDAO(sessionFactory);
    }


}
