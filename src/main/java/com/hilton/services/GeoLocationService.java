package com.hilton.services;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.errors.GeoLocationNotFoundException;

public interface GeoLocationService {
    GeoLocationDTO saveGeoLocation(GeoLocation geoLocation);

    void updateGeoLocation(GeoLocation geoLocation);

    GeoLocationDTO getGeoLocationFromAPI(String query);

    GeoLocationDTO getGeroLocationFromDataSource(String query) throws GeoLocationNotFoundException;
}
