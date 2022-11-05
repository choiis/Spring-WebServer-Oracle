package com.singer.infrastructure.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Component
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyUtil {

    @Value("${aws.s3.temp.path}")
    private String s3FilePath;

    @Value("${aws.s3.endpoint}")
    private String s3Endpoint;

    @Value("${aws.s3.bucket.name}")
    private String s3Bucket;

    @Value("${aws.s3.stream.path}")
    private String s3StreamPath;

    @Value("${aws.s3.access}")
    private String s3access;

    @Value("${aws.s3.secret}")
    private String s3secret;
}
