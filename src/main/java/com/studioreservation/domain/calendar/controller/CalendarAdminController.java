package com.studioreservation.domain.calendar.controller;

import com.studioreservation.domain.calendar.service.CalendarService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/metadata")
@SecurityRequirement(name = "Bearer Authentication")
public class CalendarAdminController {
    private final CalendarService service;

    @GetMapping
    public APIResponse<?> getAllMetaData(PageRequestDTO requestDTO) {
        return APIResponse.success(service.getAllMetaData(requestDTO));
    }
}
