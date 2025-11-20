package com.singer.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public record S3Properties(
        String tempPath,
        String endpoint,
        String bucketName,
        String streamPath,
        String access,
        String secret
) {
}
