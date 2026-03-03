package com.myblog.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogConfiguration extends Configuration {

    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();
}
