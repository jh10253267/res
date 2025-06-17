package com.studioreservation.domain.platform.controller;

import com.studioreservation.global.response.APIResponse;
import com.studioreservation.domain.platform.dto.PlatformReqDTO;
import com.studioreservation.domain.platform.service.PlatformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/platform")
@SecurityRequirement(name = "Bearer Authentication")
public class PlatformController {
    private final PlatformService service;

    @PostMapping
    public APIResponse<?> makePlatform(@RequestBody PlatformReqDTO platformReqDTO) {
        return APIResponse.success(service.makePlatform(platformReqDTO));
    }

    @PutMapping("/{cd}")
    public APIResponse<?> editPlatform(@RequestBody PlatformReqDTO platformReqDTO,
                                       @PathVariable("cd") Long cd) {
        service.editPlatform(platformReqDTO, cd);

        return APIResponse.success();
    }
}
