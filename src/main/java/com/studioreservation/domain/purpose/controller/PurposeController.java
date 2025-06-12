package com.studioreservation.domain.purpose.controller;

import com.studioreservation.domain.purpose.service.PurposeService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purposes")
public class PurposeController {
    private final PurposeService service;

    @GetMapping
    @Operation(summary = "모든 촬영 목적 가져오기", description = "모든 촬영 목적 가져오기")
    public APIResponse<?> getAllPurposes() {
        return APIResponse.success(service.getAllPurposes());
    }
}
