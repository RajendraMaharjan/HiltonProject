package com.hilton.services.adapters;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import org.modelmapper.ModelMapper;

public class GeoLocationMapper {
    GeoLocationMapper mapper;

    private final InnerClass innerClass = new InnerClass();

    static class InnerClass {
        public GeoLocationDTO getDTOFromGeo(GeoLocation geoLocation) {
            return modelMapper.map(geoLocation, GeoLocationDTO.class);
        }

        public GeoLocation getGeoFromDTO(GeoLocationDTO geoLocationDTO) {
            return modelMapper.map(geoLocationDTO, GeoLocation.class);
        }
    }

    static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
    }

    public GeoLocationDTO getGeoLocationDTOFromGeoLocation(GeoLocation geoLocation) {
        return innerClass.getDTOFromGeo(geoLocation);
//        return modelMapper.map(geoLocation, GeoLocationDTO.class);
    }

    public GeoLocation getGeoLocationFromGeoLocationDTO(GeoLocationDTO geoLocationDTO) {
//        return modelMapper.map(geoLocationDTO, GeoLocation.class);
        return innerClass.getGeoFromDTO(geoLocationDTO);

    }
}
