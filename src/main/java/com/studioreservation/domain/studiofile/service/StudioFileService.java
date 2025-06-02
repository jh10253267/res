package com.studioreservation.domain.studiofile.service;
import org.springframework.web.multipart.MultipartFile;

import com.studioreservation.domain.studiofile.repository.StudioFileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudioFileService {

	private final StudioFileRepository studioFileRepository;

	public void upload(MultipartFile multipartFile) {

	}
}
