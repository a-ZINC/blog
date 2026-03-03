package com.myblog.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.myblog.common.filter.TraceFilter;
import com.myblog.service.guice.BlogModule;
import com.myblog.service.resource.ArticleResource;
import com.myblog.service.resource.HelloResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlogApplication extends Application<BlogConfiguration> {

    public static void main(String[] args) throws Exception {
        new BlogApplication().run(args);
    }

    @Override
    public void run(BlogConfiguration blogConfiguration, Environment environment) throws Exception {
        log.info("Starting Blog Service with configuration: {}", blogConfiguration);

        Injector injector = Guice.createInjector(
                new BlogModule(blogConfiguration)
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
        super.initialize(bootstrap);
    }

    @Override
    public String getName() {
        return super.getName();
    }


}
