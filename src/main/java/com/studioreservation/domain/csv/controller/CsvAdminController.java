package com.studioreservation.domain.csv.controller;

import com.studioreservation.domain.csv.service.CsvService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/csv")
@SecurityRequirement(name = "Bearer Authentication")
public class CsvAdminController {
    private final CsvService service;

    @PostMapping
    public APIResponse<?> csvBulkUpload(@RequestParam("file") MultipartFile file) throws IOException {
        service.csvBulkUpload(file);

        return APIResponse.success();
    }
}
