package com.singer.bean;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.singer.exception.CommonExceptionHandler;

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

}
