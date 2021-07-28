package com.singer.util;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyUtil {

	@Resource(name = "properties")
	@Getter(value = AccessLevel.NONE)
	private Properties properties;

	private String s3StreamPath;

	private String s3FilePath;

	@PostConstruct
	private void init() {
		s3FilePath = properties.getProperty("global.temp.path");
		s3StreamPath = properties.getProperty("global.stream.path");
	}
}
