package com.studioreservation.domain.apiuser.controller;

import com.studioreservation.domain.apiuser.dto.RefreshTokenDTO;
import org.springframework.http.ResponseEntity;

import com.studioreservation.domain.apiuser.dto.SignupRequestDTO;
import com.studioreservation.domain.apiuser.service.UserService;
import com.studioreservation.global.response.APIResponse;

import org.springframework.web.bind.annotation.*;

import com.studioreservation.domain.apiuser.dto.LoginRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "OK";
	}

	@Operation(summary = "로그인", description = "사용자 로그인 API")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "401", description = "인증 실패")
	})
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
		return ResponseEntity.ok("로그인은 필터에서 처리됩니다.");
	}

	@Operation(summary = "토큰 리프레싱", description = "토큰 리프레싱 API")
	@PostMapping("/refreshToken")
	public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
		return ResponseEntity.ok("리프레싱은 필터에서 처리됩니다.");
	}
}
