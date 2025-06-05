package com.studioreservation.domain.studiofile.service;
import org.springframework.web.multipart.MultipartFile;

import com.studioreservation.domain.studiofile.repository.StudioFileRepository;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class StudioFileService {

	private final StudioFileRepository Repository;
	private final S3Service s3Service;

	public void upload(MultipartFile multipartFile) throws IOException {
		String url = s3Service.saveFile(multipartFile);

	}
}
