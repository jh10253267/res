package com.studioreservation.domain.featuretoggle.controller;

import com.studioreservation.domain.featuretoggle.dto.FeatureToggleReqDTO;
import com.studioreservation.domain.featuretoggle.service.FeatureToggleService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/features")
@SecurityRequirement(name = "Bearer Authentication")
public class FeatureToggleController {
    private final FeatureToggleService service;

    @GetMapping
    @Operation(summary = "모든 기능 목록 조회", description = "모든 기능 목록 조회")
    public APIResponse<?> getAllFeature() {
        return APIResponse.success(service.getAllFeature());
    }

    @PostMapping
    @Operation(summary = "기능 등록", description = "기능 등록")
    public APIResponse<?> registerFeature(FeatureToggleReqDTO reqDTO) {
        service.registerFeature(reqDTO);

        return APIResponse.success();
    }

    @PutMapping("/{featureName}")
    @Operation(summary = "기능 수정", description = "기능 수정")
    public APIResponse<?> updateFeature(@RequestBody FeatureToggleReqDTO dto,
                                        @PathVariable("featureName") String featureName) {
        service.updateFeature(dto, featureName);

        return APIResponse.success();
    }
}
