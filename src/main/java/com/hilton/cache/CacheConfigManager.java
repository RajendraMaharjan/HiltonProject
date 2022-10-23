package com.hilton.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.services.GeoLocationServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheConfigManager {
    private CacheConfigManager() {
    }

    private static LoadingCache<String, GeoLocationDTO> guavaLocCache;

    private static CacheConfigManager manager;

    public static CacheConfigManager getCacheConfigManager() {
        if (manager == null) {
            return new CacheConfigManager();
        }
        return manager;
    }

    public void initGeoLocationCache(GeoLocationServiceImpl geoLocationService) {
        if (guavaLocCache == null) {
            guavaLocCache = CacheBuilder
                    .newBuilder()
                    .concurrencyLevel(10)
                    .maximumSize(50) //maximum 50 number of caching records
                    .expireAfterAccess(1, TimeUnit.MINUTES)
                    .recordStats()
                    .build(new CacheLoader<String, GeoLocationDTO>() {
                        @Override
                        public GeoLocationDTO load(String query) {
                            return geoLocationService.getGeoLocationFromAPI(query);
                        }
                    });
        }
    }

    public GeoLocationDTO getGeoLocationDataFromCache(String key) {
        try {
            log.info("CacheStats => ", guavaLocCache.stats());
            return guavaLocCache.get(key);
        } catch (Exception e) {
            log.error("Guava GeoLocation Cache retrieval failed." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void test(GeoLocationServiceImpl geoLocationService) {
        System.out.println();
    }
}
