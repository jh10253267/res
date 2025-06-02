package com.studioreservation.domain.studiofile.service;

import java.net.URI;

import org.springframework.web.multipart.MultipartFile;

import com.studioreservation.domain.studiofile.repository.StudioFileRepository;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@RequiredArgsConstructor
public class StudioFileService {
	private final S3Client s3Client;
	private final StudioFileRepository studioFileRepository;

	public void upload(MultipartFile multipartFile) {

	}
}
