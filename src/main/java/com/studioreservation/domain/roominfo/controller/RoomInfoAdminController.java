package com.studioreservation.domain.roominfo.controller;

import com.studioreservation.domain.roominfo.dto.RoomInfoRequestDTO;
import com.studioreservation.domain.roominfo.service.RoomInfoAdminService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin/roominfos")
public class RoomInfoAdminController {
    private final RoomInfoAdminService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public APIResponse<?> createRoomInfo(@RequestBody RoomInfoRequestDTO requestDTO) {
        return APIResponse.success(service.createRoomInfo(requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{cd}")
    public APIResponse<?> updateRoomInfo(
            @PathVariable("cd") Long cd,
            @RequestBody RoomInfoRequestDTO requestDTO) {
        return APIResponse.success(service.updateRoomInfo(cd, requestDTO));
    }


}
