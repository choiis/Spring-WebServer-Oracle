package com.singer.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

@Component("s3util")
public class S3Util {

    @Autowired
    private AmazonS3 s3client;

    private String bucketName;

    @Autowired
    private PropertyUtil propertyUtil;

    @PostConstruct
    private void init() throws Exception {
        bucketName = propertyUtil.getS3Bucket();
    }

    public InputStream getS3FileStream(@NonNull String fileName) {
        S3Object s3Object = s3client.getObject(bucketName, fileName);

        return s3Object.getObjectContent();
    }

    public void putS3File(@NonNull String fileName, File file) {
        s3client.putObject(bucketName, fileName, file);
    }

    public ObjectMetadata getS3Meta(@NonNull String fileName) {
        S3Object s3Object = s3client.getObject(bucketName, fileName);

        return s3Object.getObjectMetadata();
    }

    public void deleteS3File(@NonNull String fileName) {
        s3client.deleteObject(bucketName, fileName);
    }

    public URL getS3Url(String fileName) {
        return s3client.getUrl(bucketName, fileName);
    }
}
