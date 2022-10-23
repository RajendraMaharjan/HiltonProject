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
    public void updateGeoLocation(GeoLocation geoLocation) {
        geoLocationDAO.updateGeoLocation(geoLocation);
    }

    @Override
    public GeoLocationDTO getGeoLocationFromAPI(String query) {
        GeoLocationDTO glDTO = null;
        try {
            glDTO = getGeroLocationFromDataSource(query);

            /**
             * Update data from API if the last update Time crossed five minutes than
             * recent API hit time
             */

            if (glDTO != null) {
                LocalDateTime currentTime = LocalDateTime.now();
                if (currentTime.minusMinutes(5L)
                        .isAfter(glDTO.getUpdatedTime())) {

                    LocalDateTime createdTime = glDTO.getCreatedTime();

                    //Session Issue here - NEED TO LOOK!!
                    glDTO = client.target(IP_API_URL + query)
                            .request()
                            .get()
                            .readEntity(GeoLocationDTO.class);

                    glDTO.setCreatedTime(createdTime);

                    //Session ISSUE
                    //updateAndCreated time is handled by  hibernate tags
                    updateGeoLocation(GeoLocationMapper.getGeoLocationFromGeoLocationDTO(glDTO));

                }
            } else {
                glDTO = client.target(IP_API_URL + query)
                        .request()
                        .get()
                        .readEntity(GeoLocationDTO.class);

                //updateAndCreated time is handled by  hibernate tags
                saveGeoLocation(GeoLocationMapper.getGeoLocationFromGeoLocationDTO(glDTO));
            }

            //for showing created and updated time to user response //as NUll is appearing
            if (glDTO != null) {
                glDTO.setCreatedTime(LocalDateTime.now());
                glDTO.setUpdatedTime(LocalDateTime.now());
            }

        } catch (Exception e) {
            log.error("Exception occured @getGeoLocationFromAPI => " + e.getMessage());
        }
        return glDTO;
    }

    @Override
    public GeoLocationDTO getGeroLocationFromDataSource(String query) throws GeoLocationNotFoundException {
        Optional<GeoLocation> geoLocation = geoLocationDAO.findByQuery(query);

        if (geoLocation.isPresent()) {
            log.info("GeoLocation is found in the Data Source.");
            return GeoLocationMapper.getGeoLocationDTOFromGeoLocation(geoLocation.get());
        } else {
            log.info("GeoLocation is missing in Data Source.");
//            throw new GeoLocationNotFoundException("GeoLocation is missing in Data Source.");
            return null;
        }
    }

}
