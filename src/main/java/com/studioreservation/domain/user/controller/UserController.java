package com.studioreservation.domain.user.controller;

import org.springframework.http.ResponseEntity;

import com.studioreservation.domain.user.dto.SignupRequestDTO;
import com.studioreservation.domain.user.service.UserService;
import com.studioreservation.global.response.APIResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studioreservation.domain.user.dto.LoginRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(summary = "회원가입", description = "사용자 회원가입 API")
	@PostMapping("/api/signup")
	public APIResponse<?> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
		userService.signup(signupRequestDTO);

		return APIResponse.success();
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
	@GetMapping("/refreshToken")
	public ResponseEntity<String> refreshToken() {
		return ResponseEntity.ok("리프레싱은 필터에서 처리됩니다.");
	}
}
