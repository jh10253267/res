package com.studioreservation.domain.shootingrequest.controller;

import com.studioreservation.domain.shootingrequest.service.ShootingRequestService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/shootingrequests")
@RequiredArgsConstructor
public class ShootingReqAdminController {
    private final ShootingRequestService service;

    @GetMapping
    @Operation(summary = "여러건 조회", description = "여러건 조회")
    public APIResponse<?> getAllShootingRequests(PageRequestDTO requestDTO) {
        return APIResponse.success(service.getAllShootingRequests(requestDTO));
    }

    @GetMapping("/{strtDt}")
    @Operation(summary = "여러건 조회 + strtDt보다 큰 데이터", description = "여러건 조회 + strtDt보다 큰 데이터")
    public APIResponse<?> getAllShootingRequestsByStrtDt(PageRequestDTO requestDTO) {
        return APIResponse.success(service.getAllShootingRequestsByStrtDt(requestDTO));
    }

    @GetMapping("/{sn}")
    @Operation(summary = "단건 조회", description = "단건 조회")
    public APIResponse<?> getShootingRequest(@PathVariable("sn") Long sn) {
        return APIResponse.success(service.getShootingRequest(sn));
    }
}
