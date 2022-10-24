package com.hilton.core.dto;

import com.hilton.core.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoLocationDTO {
    private String query;
    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String city;
    private String zip;
    private double lat;
    private double lon;
    private String timezone;
    private String isp;
    private String org;
    private String as;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocationDTO that = (GeoLocationDTO) o;
        return Double.compare(that.lat, lat) == 0
                && Double.compare(that.lon, lon) == 0
                && query.equals(that.query)
                && country.equals(that.country)
                && countryCode.equals(that.countryCode)
                && region.equals(that.region)
                && regionName.equals(that.regionName)
                && city.equals(that.city)
                && zip.equals(that.zip)
                && timezone.equals(that.timezone)
                && isp.equals(that.isp)
                && org.equals(that.org)
                && as.equals(that.as);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, country, countryCode, region, regionName, city, zip, lat, lon, timezone, isp, org, as);
    }
}
