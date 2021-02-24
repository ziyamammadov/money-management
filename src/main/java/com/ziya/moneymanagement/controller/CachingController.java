package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.service.CachingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CachingController {

    private final CachingService cachingService;

    public CachingController(CachingService cachingService) {
        this.cachingService = cachingService;
    }

    @GetMapping("/clearAllCaches")
    public void clearAllCaches() {
        cachingService.evictAllCaches();
    }
}
