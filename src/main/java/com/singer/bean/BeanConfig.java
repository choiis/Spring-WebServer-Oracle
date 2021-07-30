package com.singer.bean;

import java.util.Properties;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.singer.exception.CommonExceptionHandler;
import com.singer.util.PropertyUtil;

@Configuration
public class BeanConfig {

	@Bean
	public CommonExceptionHandler exceptionHandler() {
		return new CommonExceptionHandler();
	}

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setPort(25);
		mailSender.setHost("relay.company.co.kr");
		Properties mailProperties = new Properties();

		mailProperties.put("mail.transport.protocol", "smtp");
		mailProperties.put("mail.smtp.auth", false);
		mailProperties.put("mail.smtp.starttls.enable", false);
		mailProperties.put("mail.smtp.debug", true);
		mailProperties.put("mail.smtp.ssl.enable", false);

		mailSender.setJavaMailProperties(mailProperties);
		return mailSender;
	}

	@Inject
	private PropertyUtil propertyUtil;

	@Bean
	public AmazonS3 amazonS3() {

		AWSCredentials credentials = new BasicAWSCredentials(propertyUtil.getS3access(), propertyUtil.getS3secret());

		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_NORTHEAST_1).build();
	}

}
