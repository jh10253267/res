package com.studioreservation.domain.shootingrequest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.domain.shootingrequest.dto.ShootingRequestDTO;
import com.studioreservation.domain.shootingrequest.service.ShootingRequestService;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.APIResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shootingrequests")
@RequiredArgsConstructor
public class ShootingRequestController {
	private final ShootingRequestService service;

	@PostMapping
	@Operation(description = "의뢰하기")
	public APIResponse<?> shootingRequest(@RequestBody ShootingRequestDTO shootingRequestDTO) {
		return APIResponse.success(service.shootingRequest(shootingRequestDTO));
	}


}
