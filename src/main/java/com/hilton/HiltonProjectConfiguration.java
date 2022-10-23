package com.hilton;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class HiltonProjectConfiguration extends Configuration {
    // TODO: implement service configuration
    @Valid
    private PooledDataSourceFactory dbFactory = new DataSourceFactory();

    @JsonProperty("database")
    public PooledDataSourceFactory getDbFactory() {
        return dbFactory;
    }

    @JsonProperty("database")
    public void setDbFactory(DataSourceFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
        this.jerseyClient = jerseyClient;
    }

}
