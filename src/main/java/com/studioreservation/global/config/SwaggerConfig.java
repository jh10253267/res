package com.studioreservation.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(title = "Studio Reservation",
		description = "Studio Reservation Service",
		version = "v1"))

@SecurityScheme(
	name = "Bearer Authentication",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"
)

@Configuration
public class SwaggerConfig {
	@Bean
	public GroupedOpenApi publicOpenApi() {
		return GroupedOpenApi.builder()
			.group("Reservation")
			.pathsToMatch("/**")
			.build();
	}
}
