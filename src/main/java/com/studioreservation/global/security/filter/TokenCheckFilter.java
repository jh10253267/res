package com.studioreservation.global.security.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studioreservation.global.security.JWTUtil;
import com.studioreservation.global.security.exception.AccessTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenCheckFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;
	private final Map<String, Set<String>> publicPaths;

	public TokenCheckFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		this.publicPaths = Map.of(
			"/login", Set.of("POST"),
			"/api/signup", Set.of("POST"),
			"/api/rooms", Set.of("GET"));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("!!!!");
		System.out.println(SecurityContextHolder.getContext());

		if (isPublicPath(request.getRequestURI(), request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Map<String, Object> value = validateAccessToken(request);
			Authentication authentication = new UsernamePasswordAuthenticationToken(value.get("username"),
				null, null);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);
		} catch (AccessTokenException e) {
			e.sendResponseError(response);
		}
	}

	private boolean isPublicPath(String requestUri, String method) {
		Set<String> allowedMethods = publicPaths.get(requestUri);
		return allowedMethods != null && allowedMethods.contains(method);
	}

	private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
		String headerStr = request.getHeader("Authorization");

		if (headerStr == null || headerStr.length() < 8) {
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
		}

		String tokenType = headerStr.substring(0, 6);
		String tokenStr = headerStr.substring(7);

		if (tokenType.equalsIgnoreCase("Bearer") == false) {
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
		}

		try {
			Map<String, Object> values = jwtUtil.validateToken(tokenStr);
			return values;
		} catch (MalformedJwtException malformedJwtException) {
			log.error("MalformedJwtException");
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
		} catch (SignatureException signatureException) {
			log.error("SignatureException");
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
		} catch (ExpiredJwtException expiredJwtException) {
			log.error("ExpiredJwtException");
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
		}
	}
}
