package com.hilton.services;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.db.GeoLocationDAO;
import com.hilton.errors.GeoLocationNotFoundException;
import com.hilton.services.adapters.GeoLocationMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.time.LocalDateTime;
import java.util.Optional;

public class GeoLocationServiceImpl implements GeoLocationService {

    Logger logger = LoggerFactory.getLogger(GeoLocationServiceImpl.class.getSimpleName());
    private final String IP_API_URL = "http://ip-api.com/json/";
    private final GeoLocationDAO geoLocationDAO;

    private Client client;

    public GeoLocationServiceImpl(GeoLocationDAO geoLocationDAO, Client client) {
        this.geoLocationDAO = geoLocationDAO;
        this.client = client;
    }

    @Override
    public GeoLocationDTO saveGeoLocation(GeoLocation geoLocation) {
        logger.info("saveGeoLocation => GeoLocation Saved.");
        return GeoLocationMapper
                .getGeoLocationDTOFromGeoLocation(geoLocationDAO.recordGeoLocation(geoLocation));
    }

    @Override
    public void updateGeoLocation(GeoLocation geoLocation) {
        logger.info("updateGeoLocation => GeoLocation Updated.");

        //Currently updateGeoLocation is returning Object, cast is by checking instanceof if needed
        geoLocationDAO.updateGeoLocation(geoLocation);
    }

    @Override
    public GeoLocationDTO getGeoLocationFromAPI(String query) {
        logger.info("GeoLocation searching from API or data source.");

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
            logger.error("Exception occured @getGeoLocationFromAPI => " + e.getMessage());
        }
        return glDTO;
    }

    @Override
    public GeoLocationDTO getGeroLocationFromDataSource(String query) throws GeoLocationNotFoundException {
        logger.info("GeoLocation searching from data source.");

        Optional<GeoLocation> geoLocation = geoLocationDAO.findByQuery(query);

        if (geoLocation.isPresent()) {
            logger.info("GeoLocation is found in the Data Source.");
            return GeoLocationMapper.getGeoLocationDTOFromGeoLocation(geoLocation.get());
        } else {
            logger.info("GeoLocation is missing in Data Source.");
//            throw new GeoLocationNotFoundException("GeoLocation is missing in Data Source.");
            return null;
        }
    }

}
