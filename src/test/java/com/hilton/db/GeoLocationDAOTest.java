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
import org.junit.jupiter.api.DisplayName;
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
            .addResource(new GeoLocationResource(new GeoLocationServiceImpl(DAO,null)))
            .build();

    @AfterEach
    void tearsDown() {
        reset(DAO);
    }

    @BeforeEach
    void setsUp() {
        //add here
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

        when(DAO.findByQuery("24.48.0.1")).thenReturn(geoloc);
    }

    @Test
    public void testFindByQuery() {

        String query = "24.48.0.1";
        GeoLocation found = RULE.target("/geoloc/" + query).request().get(GeoLocation.class);

        assertThat(found.getQuery()).isEqualTo(query);
        verify(DAO).findByQuery("24.48.0.1");
    }
}