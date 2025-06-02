package org.kpiwebdevelopment.imageprocessingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.kpiwebdevelopment.imageprocessingservice.config.properties.AmazonS3Properties;
import org.kpiwebdevelopment.imageprocessingservice.service.ImageStorageService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    private final AmazonS3Properties amazonS3Properties;
    private final S3Client s3Client;

    @Override
    public void uploadImage(String keyName, byte[] image) {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(amazonS3Properties.getBucket())
                .key(keyName)
                .contentType("image/jpeg")
                .build();
        s3Client.putObject(putRequest, RequestBody.fromBytes(image));
    }
}
