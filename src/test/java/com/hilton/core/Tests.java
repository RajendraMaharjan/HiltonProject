package com.hilton.core;

import com.hilton.core.dto.GeoLocationDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class Tests {
    private Client client;

    private final String IP_API_URL = "http://ip-api.com/json/";
    private String IP = "24.48.0.1";


    @Before
    public void mandatory() {
        log.info("Before started.");
        IP = "24.48.0.1";
        client = Client.create();
    }


    @Test
    public void whenAPIUrlIsHit_GiveResponseForSameQueryIp() {
        WebResource webResource = client.resource(IP_API_URL + IP);
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
        TestGeoLoc dto = response.getEntity(TestGeoLoc.class);

        System.out.println(dto);
        assertEquals(dto.getQuery(), IP);
    }
}
