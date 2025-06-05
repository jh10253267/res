package com.studioreservation.domain.studiofile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    @Value("${ncp.object-storage.bucket-name}")
    private String bucketName;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = Optional.ofNullable(multipartFile.getOriginalFilename())
                .orElse("unnamed.file");
        String savedFilename = UUID.randomUUID() + "_" + originalFilename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(savedFilename)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                // 메타데이터를 추가하고 싶으면 아래처럼 Map<String, String> 형태로 넣을 수 있음
                .metadata(Collections.singletonMap("custom-meta-key", "custom-meta-value"))
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

        // 업로드된 파일의 URL 생성 (엔드포인트 + 버킷 + 키 형태)
        String url = String.format("https://kr.object.ncloudstorage.com/%s/%s", bucketName, savedFilename);

        return url;
    }
}
