package com.studioreservation.global.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
	@Value("${jwt.secret.key}")
	private String keyString;
	private Key key;

	@PostConstruct
	public void initKey() {
		this.key = Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(Map<String, Object> valueMap, int days) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");

		int time = (60 * 24) * days;

		return Jwts.builder()
				.setHeader(headers)
				.setClaims(valueMap)
				.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public Map<String, Object> validateToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}