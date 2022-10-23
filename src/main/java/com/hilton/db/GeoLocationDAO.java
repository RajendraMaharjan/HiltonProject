package com.hilton.db;

import com.hilton.core.GeoLocation;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class GeoLocationDAO extends AbstractDAO<GeoLocation> {
    public GeoLocationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<GeoLocation> findByQuery(String query) {
        return Optional.of(get(query));
    }

    public String recordGeoLocation(GeoLocation geoLocation) {
        return persist(geoLocation).getQuery();
    }
}
