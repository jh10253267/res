package com.studioreservation.domain.studiofile.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.studioreservation.domain.studiofile.dto.StudioFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;
    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucketName;

    public StudioFileDTO saveFile(MultipartFile file) throws IOException {
        String originalFilename = Optional.of(file.getOriginalFilename())
                .orElse("unnamed.file");
        String savedFilename = UUID.randomUUID() + "_" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(bucketName, savedFilename, file.getInputStream(), metadata);
        String url = amazonS3.getUrl(bucketName, savedFilename).toString();

        return new StudioFileDTO(originalFilename, savedFilename, url);
    }
}
