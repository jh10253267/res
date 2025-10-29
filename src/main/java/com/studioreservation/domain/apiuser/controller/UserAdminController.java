package com.studioreservation.domain.apiuser.controller;

import com.studioreservation.domain.apiuser.dto.SignupRequestDTO;
import com.studioreservation.domain.apiuser.service.UserService;
import com.studioreservation.global.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserAdminController {
    private final UserService service;

    @Operation(summary = "회원가입", description = "사용자 회원가입 API")
    @PostMapping("/signup")
    public APIResponse<?> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        service.signup(signupRequestDTO);

        return APIResponse.success();
    }
}
