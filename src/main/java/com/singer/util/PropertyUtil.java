package com.singer.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyUtil {

	@Value("#{global['global.stream.path']}")
	private String s3StreamPath;

	@Value("#{global['global.temp.path']}")
	private String s3FilePath;

	@Value("#{global['global.aws.s3bucket']}")
	private String s3Bucket;

	@Value("#{global['global.aws.s3access']}")
	private String s3access;

	@Value("#{global['global.aws.s3secret']}")
	private String s3secret;

	@Value("#{global['global.ftp.server']}")
	private String ftpServer;

	@Value("#{global['global.ftp.port']}")
	private int ftpPort;

	@Value("#{global['global.ftp.username']}")
	private String ftpUsername;

	@Value("#{global['global.ftp.password']}")
	private String ftpPassword;

}
