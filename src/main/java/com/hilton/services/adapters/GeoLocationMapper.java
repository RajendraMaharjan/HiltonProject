package com.hilton.services.adapters;

import com.hilton.core.GeoLocation;
import com.hilton.core.dto.GeoLocationDTO;
import org.modelmapper.ModelMapper;

public class GeoLocationMapper {

    static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
    }

    public static GeoLocationDTO getGeoLocationDTOFromGeoLocation(GeoLocation geoLocation) {
        return modelMapper.map(geoLocation, GeoLocationDTO.class);
    }

    public static GeoLocation getGeoLocationFromGeoLocationDTO(GeoLocationDTO geoLocationDTO) {
        return modelMapper.map(geoLocationDTO, GeoLocation.class);
    }
}
