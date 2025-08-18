package com.studioreservation.domain.platform.controller;

import com.studioreservation.domain.platform.service.PlatformService;
import com.studioreservation.global.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/platforms")
public class PlatformController {
    private final PlatformService service;

    @GetMapping
    public APIResponse<?> getAllPlatforms() {
        return APIResponse.success(service.getAllPlatform());
    }
}