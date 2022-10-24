package com.hilton;

import com.hilton.cache.CacheConfigManager;
import com.hilton.core.GeoLocation;
import com.hilton.db.GeoLocationDAO;
import com.hilton.resources.GeoLocationResource;
import com.hilton.services.GeoLocationServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Client;

public class HiltonProjectApplication extends Application<HiltonProjectConfiguration> {
    private static final String SQL = "sql";

    Logger logger = Logger.getLogger(HiltonProjectApplication.class.getSimpleName());

    public static void main(final String[] args) throws Exception {
        new HiltonProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "HiltonProject";
    }

    @Override
    public void initialize(final Bootstrap<HiltonProjectConfiguration> bootstrap) {
        // TODO: application initialization
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final HiltonProjectConfiguration conf,
                    final Environment env) {
        // TODO: implement application

//        logger.info("runing block");
//        System.out.println(HiltonProjectConfiguration.getTest());

//        // Datasource configuration
//        final DataSource dataSource = conf.getDataSourceFactory().build(env.metrics(), SQL);
//        DBI dbi = new DBI(dataSource);

        final Client client = new JerseyClientBuilder(env)
                .using(conf.getJerseyClientConfiguration())
                .build(getName());

        final GeoLocationDAO geoLocationDAO = new GeoLocationDAO(hibernate.getSessionFactory());
        final GeoLocationServiceImpl geoLocationService = new GeoLocationServiceImpl(geoLocationDAO, client);

        final CacheConfigManager cacheConfigManager = CacheConfigManager.getCacheConfigManager();
        cacheConfigManager.initGeoLocationCache(geoLocationService);
//        cacheConfigManager.test(geoLocationService);

        //registering resource
        env.jersey().register(new GeoLocationResource(geoLocationService));

    }

    private final HibernateBundle<HiltonProjectConfiguration> hibernate
            = new HibernateBundle<HiltonProjectConfiguration>(GeoLocation.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(HiltonProjectConfiguration configuration) {
            return configuration.getDbFactory();
        }
    };

}
