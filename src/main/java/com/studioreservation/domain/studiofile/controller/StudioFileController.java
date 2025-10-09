package com.studioreservation.domain.studiofile.controller;

import com.studioreservation.domain.studiofile.service.S3Service;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class StudioFileController {
    private final S3Service service;

    @Operation(summary = "이미지 업로드")
    @PostMapping
    public APIResponse<?> imageUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return APIResponse.success(service.saveFile(file));
    }

}
