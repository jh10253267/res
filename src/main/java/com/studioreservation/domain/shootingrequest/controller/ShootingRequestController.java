package com.studioreservation.domain.shootingrequest.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.domain.shootingrequest.dto.ShootingRequestDTO;
import com.studioreservation.domain.shootingrequest.service.ShootingRequestService;
import com.studioreservation.global.response.APIResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shootingrequests")
@RequiredArgsConstructor
public class ShootingRequestController {
	private final ShootingRequestService service;

	@PostMapping
	@Operation(summary = "촬영 의뢰", description = "촬영 의뢰")
	public APIResponse<?> shootingRequest(@Valid @RequestBody ShootingRequestDTO shootingRequestDTO) {
		return APIResponse.success(service.shootingRequest(shootingRequestDTO));
	}


}
