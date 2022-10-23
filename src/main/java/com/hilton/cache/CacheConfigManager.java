package com.hilton.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.hilton.core.dto.GeoLocationDTO;
import com.hilton.services.GeoLocationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CacheConfigManager {

    Logger logger = LoggerFactory.getLogger(CacheConfigManager.class.getSimpleName());

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
            logger.info("initGeoLocationCache guavaLocCache.");

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
            logger.info("getGeoLocationDataFromCache CacheStats => ", guavaLocCache.stats());
            return guavaLocCache.get(key);
        } catch (Exception e) {
            logger.error("Guava GeoLocation Cache retrieval failed." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void test(GeoLocationServiceImpl geoLocationService) {
        System.out.println();
    }
}
