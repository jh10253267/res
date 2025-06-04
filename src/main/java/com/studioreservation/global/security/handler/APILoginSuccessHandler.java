package com.studioreservation.global.security.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;
import com.studioreservation.global.security.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
	private final JWTUtil jwtUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		Map<String, Object> claim = Map.of("username", authentication.getName(),
				"role", authentication.getAuthorities());

		String accessToken = jwtUtil.generateToken(claim, 1);
		String refreshToken = jwtUtil.generateToken(claim, 30);

		Gson gson = new Gson();

		Map<String, String> keyMap = Map.of(
				"accessToken", accessToken,
				"refreshToken", refreshToken);

		String jsonStr = gson.toJson(keyMap);

		response.getWriter().println(jsonStr);
	}
}