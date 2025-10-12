package com.studioreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudioreservationApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudioreservationApplication.class, args);
	}
}
