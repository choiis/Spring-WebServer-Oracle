package com.singer.common.bean;


import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.AwsHostNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.singer.common.exception.CommonExceptionHandler;
import com.singer.infrastructure.config.S3Properties;

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
    private S3Properties s3Properties;

    @Bean
    public AmazonS3 amazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccess(), s3Properties.getSecret());

        // minio 참고
        //https://stackoverflow.com/questions/49332533/using-s3-java-sdk-to-talk-to-s3-compatible-storage-minio
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withPathStyleAccessEnabled(true)
            .withEndpointConfiguration(
                new EndpointConfiguration(s3Properties.getEndpoint(),
                    AwsHostNameUtils.parseRegion(s3Properties.getEndpoint(), AmazonS3Client.S3_SERVICE_NAME))
            ).build();
    }

}
