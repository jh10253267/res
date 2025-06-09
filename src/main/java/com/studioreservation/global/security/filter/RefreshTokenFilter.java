package com.studioreservation.global.security.filter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.studioreservation.global.security.JWTUtil;
import com.studioreservation.global.security.exception.RefreshTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
	private final String refreshPath;
	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		ServletException, IOException {
		String path = request.getRequestURI();

		if(!path.equals(refreshPath)) {
			filterChain.doFilter(request, response);
			return;
		}

		Map<String, String> tokens = parseRequestJSON(request);

		String accessToken = tokens.get("accessToken");
		String refreshToken = tokens.get("refreshToken");
		
		try {
			checkAccessToken(accessToken);
		} catch (RefreshTokenException refreshTokenException) {
			refreshTokenException.sendResponseError(response);
			return;
		}

		Map<String, Object> refreshClaims = null;

		try {
			refreshClaims = checkRefreshToken(refreshToken);
			Integer exp = (Integer)refreshClaims.get("exp");

			Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);

			Date current = new Date(System.currentTimeMillis());

			long gapTime = (expTime.getTime() - current.getTime());

			String username = (String)refreshClaims.get("username");

			String accessTokenValue = jwtUtil.generateToken(Map.of("username", username), 1);
			String refreshTokenValue = tokens.get("refreshToken");

			if(gapTime < (1000* 60 * 60 * 24 * 3)) {
				refreshTokenValue = jwtUtil.generateToken(Map.of("username", username), 1);
			}

			sendTokens(accessTokenValue, refreshTokenValue, response);

		} catch (RefreshTokenException refreshTokenException) {
			refreshTokenException.sendResponseError(response);
			return;
		}
	}

	private Map<String, String> parseRequestJSON(HttpServletRequest request) {
		try(Reader reader = new InputStreamReader(request.getInputStream())) {
			Gson gson = new Gson();

			return gson.fromJson(reader, Map.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	private void checkAccessToken(String accessToken) throws RefreshTokenException {
		try {
			jwtUtil.validateToken(accessToken);
		} catch (ExpiredJwtException expiredJwtException) {
			log.info("Access Token has Expired");
		} catch (Exception e) {
			throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
		}
	}
	private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException {
		try {
			Map<String, Object> values = jwtUtil.validateToken(refreshToken);
			return values;
		}catch (ExpiredJwtException expiredJwtException) {
			throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
		} catch (MalformedJwtException malformedJwtException) {
			throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
		} catch(Exception e) {
			new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
		}
		return null;
	}
	private void sendTokens(String accessTokenValue, String refreshTokenValue, HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		Gson gson = new Gson();

		String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue, "refreshToken", refreshTokenValue));

		try {
			response.getWriter().println(jsonStr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
