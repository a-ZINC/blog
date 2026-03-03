package com.myblog.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.myblog.common.filter.TraceFilter;
import com.myblog.db.entity.ArticleEntity;
import com.myblog.db.entity.CommentEntity;
import com.myblog.db.entity.SeriesEntity;
import com.myblog.db.entity.UserEntity;
import com.myblog.service.guice.BlogModule;
import com.myblog.service.resource.ArticleResource;
import com.myblog.service.resource.HelloResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlogApplication extends Application<BlogConfiguration> {

    private final HibernateBundle<BlogConfiguration> hibernateBundle =
            new HibernateBundle<BlogConfiguration>(
                    UserEntity.class,
                    SeriesEntity.class,
                    ArticleEntity.class,
                    CommentEntity.class
            ) {
                @Override
                public DataSourceFactory getDataSourceFactory(BlogConfiguration blogConfiguration) {
                    return blogConfiguration.getDatabase();
                }
            };

    public static void main(String[] args) throws Exception {
        new BlogApplication().run(args);
    }

    @Override
    public void run(BlogConfiguration blogConfiguration, Environment environment) throws Exception {
        log.info("Starting Blog Service with configuration: {}", blogConfiguration);

        Injector injector = Guice.createInjector(
                new BlogModule(blogConfiguration, hibernateBundle)
        );

        environment.servlets()
                .addFilter("TraceFilter", new TraceFilter())
                .addMappingForUrlPatterns(
                        null,
                        false,
                        "/*"
                );

        environment.jersey().register(injector.getInstance(HelloResource.class));
        environment.jersey().register(injector.getInstance(ArticleResource.class));

        log.info("Blog Service started successfully and is ready to accept requests.");

    }

    @Override
    public void initialize(Bootstrap<BlogConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);

        bootstrap.addBundle(new MigrationsBundle<>(){
            @Override
            public DataSourceFactory getDataSourceFactory(BlogConfiguration blogConfiguration) {
                return blogConfiguration.getDatabase();
            }

            @Override
            public String getMigrationsFileName() {
                return "db/migrations/liquibase-master.xml";
            }
        });
    }

    @Override
    public String getName() {
        return super.getName();
    }


}
