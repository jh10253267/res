package com.studioreservation.global.security.filter;

import java.io.IOException;
import java.util.Map;

import com.studioreservation.global.security.APIUserDetailsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {
	private final APIUserDetailsService apiUserDetailsService;
	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
									@NotNull FilterChain filterChain) throws ServletException, IOException {
		String path = request.getRequestURI();

		if (!path.startsWith("/api/admin")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Map<String, Object> value = validateAccessToken(request);
			String username = value.get("username").toString();
			UserDetails userDetails = apiUserDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);
		} catch (AccessTokenException e) {
			e.sendResponseError(response);
		}
	}

	private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
		String headerStr = request.getHeader("Authorization");

		if (headerStr == null || headerStr.length() < 8) {
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
		}

		String tokenType = headerStr.substring(0, 6);
		String tokenStr = headerStr.substring(7);

		if (!tokenType.equalsIgnoreCase("Bearer")) {
			throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
		}

		try {
			return jwtUtil.validateToken(tokenStr);
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
