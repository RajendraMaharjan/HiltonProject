package com.hilton.db;

import com.hilton.core.GeoLocation;
import com.hilton.resources.GeoLocationResource;
import com.hilton.services.GeoLocationServiceImpl;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
public class GeoLocationDAOTest extends TestCase {
    private static final GeoLocationDAO DAO = mock(GeoLocationDAO.class);
    private static final GeoLocationServiceImpl GSER = mock(GeoLocationServiceImpl.class);
    public static final ResourceExtension RULE = ResourceExtension.builder()
            .addResource(new GeoLocationResource(GSER))
            .build();

    @AfterEach
    void tearsDown() {
        reset(DAO);
    }

    GeoLocation geoloc;

    @Before
    void setsUp() {
        //add here
    }

    @Test
    public void testFindByQuery() {
        geoloc = new GeoLocation();
        geoloc.setQuery("24.48.0.1");
        geoloc.setCountry("Canada");
        geoloc.setCountryCode("CA");
        geoloc.setRegion("QC");
        geoloc.setRegionName("Quebec");
        geoloc.setCity("Montreal");
        geoloc.setZip("H1K");
        geoloc.setLat(45.6085);
        geoloc.setLon(-73.5493);
        geoloc.setTimezone("America/Toronto");
        geoloc.setIsp("Le Groupe Videotron Ltee");
        geoloc.setOrg("Videotron Ltee");
        geoloc.setAs("AS5769 Videotron Telecom Ltee");

        when(DAO.findByQuery("24.48.0.1")).thenReturn(Optional.ofNullable(geoloc));

        GeoLocation found = RULE.target("/geoloc/24.48.0.1").request().get(GeoLocation.class);

        assertThat(found.getQuery()).isEqualTo(geoloc.getQuery());
        verify(DAO).findByQuery("24.48.0.1");
    }
}