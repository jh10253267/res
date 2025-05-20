package com.studioreservation.global.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.studioreservation.global.security.JWTUtil;
import com.studioreservation.global.security.filter.APILoginFilter;
import com.studioreservation.global.security.APIUserDetailsService;
import com.studioreservation.global.security.filter.RefreshTokenFilter;
import com.studioreservation.global.security.filter.TokenCheckFilter;
import com.studioreservation.global.security.handler.APILoginSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
	private static final long CORS_MAX_AGE_SEC = 86400;
	private final APIUserDetailsService apiUserDetailsService;
	private final JWTUtil jwtUtil;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(
				PathRequest.toStaticResources().atCommonLocations());
	}

	private CorsConfigurationSource configurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedOriginPatterns(Arrays.asList(
				"http://localhost:3000" // local
			));
			config.setAllowCredentials(true);
			config.setMaxAge(CORS_MAX_AGE_SEC);
			config.setExposedHeaders(List.of("*"));
			return config;
		};
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
		PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(configurationSource()))
			.sessionManagement((sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// AuthenticationManager authenticationManager =
		// 	authenticationManager(apiUserDetailsService, passwordEncoder());
		//
		// http.authenticationManager(authenticationManager);
		//
		// APILoginFilter apiLoginFilter = new APILoginFilter("/login");
		// apiLoginFilter.setAuthenticationManager(authenticationManager);
		//
		// APILoginSuccessHandler apiLoginSuccessHandler = new APILoginSuccessHandler(jwtUtil);
		// apiLoginFilter.setAuthenticationSuccessHandler(apiLoginSuccessHandler);
		//
		// http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
		// http.addFilterBefore(tokenCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
		// http.addFilterBefore(refreshTokenFilter(jwtUtil), TokenCheckFilter.class);

		return http.build();
	}

	private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil) {
		return new TokenCheckFilter(jwtUtil);
	}

	private RefreshTokenFilter refreshTokenFilter(JWTUtil jwtUtil) {
		return new RefreshTokenFilter("/refreshToken", jwtUtil);
	}
}
