package com.studioreservation.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
	@Bean
	public GroupedOpenApi publicOpenApi() {
		return GroupedOpenApi.builder()
			.group("Reservation")
			.pathsToMatch("/api/**")
			.build();
	}
}
