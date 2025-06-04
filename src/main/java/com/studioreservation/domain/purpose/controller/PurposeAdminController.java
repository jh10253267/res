package com.studioreservation.domain.purpose.controller;

import com.studioreservation.domain.purpose.dto.PurposeRequestDTO;
import com.studioreservation.domain.purpose.service.PurposeService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/purposes")
public class PurposeAdminController {
    private final PurposeService service;

    @GetMapping
    @ApiResponse(description = "모든 카테고리 가져오기")
    public APIResponse<?> getAllPurposes() {
        return APIResponse.success(service.getAllPurposes());

    }

    @PostMapping
    @ApiResponse(description = "카테고리 생성")
    public APIResponse<?> createPurpose(PurposeRequestDTO requestDTO) {
        return APIResponse.success(service.createPurpose(requestDTO));
    }

    @PutMapping("/{cd}")
    @ApiResponse(description = "카테고리 수정")
    public APIResponse<?> editPurpose(@PathVariable("cd") Long cd,
                                      @RequestBody PurposeRequestDTO requestDTO) {
        return APIResponse.success(service.editPurpose(requestDTO, cd));
    }
}
