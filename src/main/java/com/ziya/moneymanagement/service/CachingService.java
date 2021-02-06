package com.ziya.moneymanagement.service;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CachingService {
    private CacheManager cacheManager;

    public CachingService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictAllCaches() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}