package com.singer.common.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.singer.common.exception.CommonExceptionHandler;
import com.singer.infrastructure.util.PropertyUtil;

@Configuration
public class BeanConfig {

    @Bean
    public CommonExceptionHandler exceptionHandler() {
        return new CommonExceptionHandler();
    }

    @Bean(name = "multipartResolver")
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Autowired
    private PropertyUtil propertyUtil;

    @Bean
    public AmazonS3 amazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials(propertyUtil.getS3access(), propertyUtil.getS3secret());

        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.AP_NORTHEAST_1).build();
    }

}
