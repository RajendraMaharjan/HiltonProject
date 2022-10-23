package com.hilton.resources;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.db.GeoLocationDAO;
import com.hilton.services.GeoLocationServiceImpl;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import junit.framework.TestCase;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class GeoLocationResourceTest extends TestCase {
//
//    private static final GeoLocationDAO GLOC_DAO = mock(GeoLocationDAO.class);
//    private static final GeoLocationServiceImpl geoLocationService = mock(GeoLocationServiceImpl.class);
//
//    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
//            .addResource(new GeoLocationResource(geoLocationService))
//            .build();
//
//    private final ArgumentCaptor<GeoLocation> gLocCaptor = ArgumentCaptor.forClass(GeoLocation.class);
//    private GeoLocation geoloc;
//
//    @BeforeEach
//    void setsUp() {
//        geoloc = new GeoLocation();
//        geoloc.setQuery("24.48.0.1");
//        geoloc.setCountry("Canada");
//        geoloc.setCountryCode("CA");
//        geoloc.setRegion("QC");
//        geoloc.setRegionName("Quebec");
//        geoloc.setCity("Montreal");
//        geoloc.setZip("H1K");
//        geoloc.setLat(45.6085);
//        geoloc.setLon(-73.5493);
//        geoloc.setTimezone("America/Toronto");
//        geoloc.setIsp("Le Groupe Videotron Ltee");
//        geoloc.setOrg("Videotron Ltee");
//        geoloc.setAs("AS5769 Videotron Telecom Ltee");
//
//    }
//
//    @Test
//    public void testQueryGeoLocation() {
//        final Response response = RESOURCES.target("/geoloc/")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .post(Entity.entity(geoloc, MediaType.APPLICATION_JSON_TYPE));
//
////        assertThat(response.getStatus()).isEqualTo(Response.Status.OK);
////        verify(GLOC_DAO).create(gLocCaptor.capture());
////        AssertionsForClassTypes.assertThat(gLocCaptor.getValue()).isEqualTo(gLocCaptor);
//    }
//
//    @AfterEach
//    void tearsDown() {
//        reset(GLOC_DAO);
//    }
}