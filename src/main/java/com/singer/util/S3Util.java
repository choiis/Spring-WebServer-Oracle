package com.singer.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

@Component("s3util")
public class S3Util {

	private Regions clientRegion = Regions.AP_NORTHEAST_2;

	@Resource(name = "properties")
	private Properties properties;

	private String access;
	private String secret;
	private AmazonS3 s3client;

	private String bucketName;

	@PostConstruct
	private void init() throws Exception {

		access = properties.getProperty("global.aws.s3access");
		secret = properties.getProperty("global.aws.s3secret");
		bucketName = properties.getProperty("global.aws.s3bucket");

		AWSCredentials credentials = new BasicAWSCredentials(access, secret);

		s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(clientRegion).build();
	}

	public InputStream getS3FileStream(String fileName) {
		S3Object s3Object = s3client.getObject(bucketName, fileName);

		return s3Object.getObjectContent();
	}

	public void putS3File(String fileName, File file) {
		s3client.putObject(bucketName, fileName, file);
	}
}
