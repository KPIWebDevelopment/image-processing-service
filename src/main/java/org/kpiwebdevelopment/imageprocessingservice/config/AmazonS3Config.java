package org.kpiwebdevelopment.imageprocessingservice.config;

import lombok.RequiredArgsConstructor;
import org.kpiwebdevelopment.imageprocessingservice.config.properties.AmazonS3Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class AmazonS3Config {

    private final AmazonS3Properties amazonS3Properties;

    @Bean
    public AwsBasicCredentials awsBasicCredentials() {
        return AwsBasicCredentials.create(
                amazonS3Properties.getAccessKey(),
                amazonS3Properties.getSecretKey()
        );
    }

    @Bean
    public S3Client s3Client(AwsBasicCredentials awsBasicCredentials) {
        return S3Client.builder()
                .region(Region.of(amazonS3Properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

}
