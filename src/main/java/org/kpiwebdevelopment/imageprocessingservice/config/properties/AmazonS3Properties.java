package org.kpiwebdevelopment.imageprocessingservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "amazon.s3")
public class AmazonS3Properties {
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String region;
}
