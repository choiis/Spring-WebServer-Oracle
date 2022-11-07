package com.singer.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
@Getter
@ConfigurationProperties(prefix = "aws.s3")
public class S3Properties {

    @Value("${aws.s3.temp.path}")
    private String tempPath;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.stream.path}")
    private String streamPath;

    @Value("${aws.s3.access}")
    private String access;

    @Value("${aws.s3.secret}")
    private String secret;
}
