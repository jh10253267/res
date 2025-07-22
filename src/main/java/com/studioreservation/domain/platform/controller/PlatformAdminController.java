package com.studioreservation.domain.platform.controller;

import com.studioreservation.global.response.APIResponse;
import com.studioreservation.domain.platform.dto.PlatformReqDTO;
import com.studioreservation.domain.platform.service.PlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/platforms")
@SecurityRequirement(name = "Bearer Authentication")
public class PlatformAdminController {
    private final PlatformService service;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "플랫폼 수정", description = "픞랫폼 수정")
    @PutMapping("/{cd}")
    public APIResponse<?> updatePlatform(@RequestBody PlatformReqDTO platformReqDTO,
                                       @PathVariable("cd") Long cd) {
        service.updatePlatform(platformReqDTO, cd);

        return APIResponse.success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "플랫폼 생성", description = "픞랫폼 생성")
    @PostMapping
    public APIResponse<?> makePlatform(@RequestBody PlatformReqDTO platformReqDTO) {
        return APIResponse.success(service.makePlatform(platformReqDTO));
    }
}
