package com.hilton.services;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.db.GeoLocationDAO;
import com.hilton.errors.GeoLocationNotFoundException;
import com.hilton.services.adapters.GeoLocationMapper;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class GeoLocationServiceImpl implements GeoLocationService {

    private final String IP_API_URL = "http://ip-api.com/json/";
    private final GeoLocationDAO geoLocationDAO;

    private Client client;

    public GeoLocationServiceImpl(GeoLocationDAO geoLocationDAO, Client client) {
        this.geoLocationDAO = geoLocationDAO;
        this.client = client;
    }

    @Override
    public String saveGeoLocation(GeoLocation geoLocation) {
        return geoLocationDAO.recordGeoLocation(geoLocation);
    }

    @Override
    public GeoLocationDTO getGeoLocationFromAPI(String query) {

        return null;
    }

    @Override
    public GeoLocationDTO getGeroLocationFromDataSource(String query) throws GeoLocationNotFoundException {

        return null;
    }

}
