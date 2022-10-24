package com.hilton.services;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.db.GeoLocationDAO;
import com.hilton.errors.GeoLocationNotFoundException;
import com.hilton.services.adapters.GeoLocationMapper;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import junit.framework.TestCase;
import org.junit.Test;

import javax.ws.rs.client.Client;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeoLocationServiceImplTest {

    String query = "24.48.0.1";

    @Test
    public void testGetGeroLocationFromDataSource() throws GeoLocationNotFoundException, NoSuchFieldException, IllegalAccessException {
        Optional<GeoLocation> geoloc = Optional.ofNullable(GeoLocation.builder()
                .query("24.48.0.1")
                .country("Canada")
                .countryCode("CA")
                .region("QC")
                .regionName("Quebec")
                .city("Montreal")
                .zip("H1K")
                .lat(45.6085)
                .lon(-73.5493)
                .timezone("America/Toronto")
                .isp("Le Groupe Videotron Ltee")
                .org("Videotron Ltee")
                .as("AS5769 Videotron Telecom Ltee")
                .build());

        Optional<GeoLocationDTO> geolocDTO = Optional.ofNullable(GeoLocationDTO.builder()
                .query("24.48.0.1")
                .country("Canada")
                .countryCode("CA")
                .region("QC")
                .regionName("Quebec")
                .city("Montreal")
                .zip("H1K")
                .lat(45.6085)
                .lon(-73.5493)
                .timezone("America/Toronto")
                .isp("Le Groupe Videotron Ltee")
                .org("Videotron Ltee")
                .as("AS5769 Videotron Telecom Ltee")
                .build());


        GeoLocationDAO mockGeoLocDAO = mock(GeoLocationDAO.class);
        when(mockGeoLocDAO.findByQuery(anyString()))
                .thenReturn(geoloc);

        Client client = mock(Client.class);
        GeoLocationServiceImpl geoLocationService = new GeoLocationServiceImpl(mockGeoLocDAO, client);

        GeoLocationMapper mapper = mock(GeoLocationMapper.class);
        Field f1 = geoLocationService.getClass().getDeclaredField("geoLocationMapper");
        f1.setAccessible(true);
        f1.set(geoLocationService, mapper);

        when(mapper.getGeoLocationDTOFromGeoLocation(any()))
                .thenReturn(geolocDTO.get());

        GeoLocationDTO res = geoLocationService.getGeroLocationFromDataSource("query");
        assertNotNull(res);

    }


    @Test
    public void testGetGeroLocationFromDataSource_Else() throws GeoLocationNotFoundException, NoSuchFieldException, IllegalAccessException {
        Optional<GeoLocation> geoloc = Optional.ofNullable(GeoLocation.builder()
                .query("24.48.0.1")
                .country("Canada")
                .countryCode("CA")
                .region("QC")
                .regionName("Quebec")
                .city("Montreal")
                .zip("H1K")
                .lat(45.6085)
                .lon(-73.5493)
                .timezone("America/Toronto")
                .isp("Le Groupe Videotron Ltee")
                .org("Videotron Ltee")
                .as("AS5769 Videotron Telecom Ltee")
                .build());

        Optional<GeoLocationDTO> geolocDTO = Optional.ofNullable(GeoLocationDTO.builder()
                .query("24.48.0.1")
                .country("Canada")
                .countryCode("CA")
                .region("QC")
                .regionName("Quebec")
                .city("Montreal")
                .zip("H1K")
                .lat(45.6085)
                .lon(-73.5493)
                .timezone("America/Toronto")
                .isp("Le Groupe Videotron Ltee")
                .org("Videotron Ltee")
                .as("AS5769 Videotron Telecom Ltee")
                .build());

        Optional<GeoLocation> tes = Optional.empty();

        GeoLocationDAO mockGeoLocDAO = mock(GeoLocationDAO.class);
        when(mockGeoLocDAO.findByQuery(anyString()))
//                .thenReturn(geoloc);
                .thenReturn(tes);

        Client client = mock(Client.class);
        GeoLocationServiceImpl geoLocationService = new GeoLocationServiceImpl(mockGeoLocDAO, client);

        GeoLocationMapper mapper = mock(GeoLocationMapper.class);
        Field f1 = geoLocationService.getClass().getDeclaredField("geoLocationMapper");
        f1.setAccessible(true);
        f1.set(geoLocationService, mapper);

        when(mapper.getGeoLocationDTOFromGeoLocation(any()))
                .thenReturn(geolocDTO.get());

        GeoLocationDTO res = geoLocationService.getGeroLocationFromDataSource("query");
        assertNull(res);

    }
}