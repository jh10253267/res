package com.studioreservation.global.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
	@Bean
	public S3Client s3Client() {
		return S3Client.builder()
			.endpointOverride(URI.create("https://kr.object.ncloudstorage.com/test-studio-bucket"))
			.credentialsProvider(StaticCredentialsProvider.create(
				AwsBasicCredentials.create("ACCESS_KEY", "SECRET_KEY")
			))
			.region(Region.AP_NORTHEAST_2)
			.build();
	}
}
