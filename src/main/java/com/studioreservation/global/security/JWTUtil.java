package com.studioreservation.global.security;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JWTUtil {
	@Value("${jwt.secret.key}")
	private String key;

	public String generateToken(Map<String,Object> valueMap, int days) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		Map<String, Object> payloads = new HashMap<>(valueMap);

		int time = (60 * 24) * days;

		return Jwts.builder()
			.setHeader(headers)
			.setClaims(payloads)
			.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
			.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
			.signWith(SignatureAlgorithm.HS256, key.getBytes())
			.compact();
	}
	public Map<String, Object> validateToken(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
			.build()
			.parseClaimsJws(token)
			.getBody();

		return claims;
	}
}