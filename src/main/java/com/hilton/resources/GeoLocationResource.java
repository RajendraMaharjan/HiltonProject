package com.hilton.resources;

import com.hilton.cache.CacheConfigManager;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.core.value.DefaultResponse;
import com.hilton.services.GeoLocationServiceImpl;
import com.hilton.services.adapters.GeoLocationMapper;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("/geoloc")
@Produces(MediaType.APPLICATION_JSON)
public class GeoLocationResource {

    static Logger logger = LoggerFactory.getLogger(GeoLocationResource.class.getSimpleName());

    private static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private GeoLocationServiceImpl geoLocationService;
    private GeoLocationMapper geoLocationMapper = new GeoLocationMapper();

    public GeoLocationResource(GeoLocationServiceImpl geoLocationService) {
        this.geoLocationService = geoLocationService;
    }

    @GET()
    @Path(value = "/healthCheck")
    public String healthCheck() {
        return "Ping received at " + LocalDateTime.now();
    }

    @GET()
    @Path(value = "/{query}")
    @UnitOfWork
    public Response queryGeoLocation(@NotNull
                                     @Pattern(regexp = IPV4_PATTERN, message = "=> Please try with a valid IP address; similar to IP Address xx.xx.xx.xx ")
                                     @PathParam("query") String query) {
        logger.info("HELLO queryGeoLocation------------------------ LOG CHECK");

//        /// TODO: 22/10/2022 DO IP FILTERING FOR QUERY
//        if (query.matches(IPV4_PATTERN)) {
//
//        }

//        GeoLocationDTO geoLocationDTO = geoLocationService.getGeoLocationFromAPI(query);
        GeoLocationDTO geoLocationDTO = geoLocationService.loadGeoLocationFromCache(query);

        if (geoLocationDTO != null) {
            return Response
                    .ok(geoLocationDTO)
                    .build();
        } else {
            DefaultResponse res = new DefaultResponse();
            res.setMessage("GeoLocation details for " + query + " " + "not found.");
            res.setStatus(Response.Status.NOT_FOUND);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(res)
                    .build();
        }
    }

    @POST
    @Path("/")
    @UnitOfWork
    public Response createGeoLocation(@NotNull
                                      @Valid GeoLocationDTO geoLocationDTO) {

        GeoLocationDTO geoLocationDTO1 = geoLocationService
                .saveGeoLocation(geoLocationMapper.getGeoLocationFromGeoLocationDTO(geoLocationDTO));
        if (geoLocationDTO1 != null) {
            return Response
                    .ok(geoLocationDTO1)
                    .build();
        } else {
            DefaultResponse res = new DefaultResponse();
            res.setMessage("GeoLocation details failed to save.");
            res.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(res)
                    .build();
        }
    }
}
