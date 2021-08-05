package com.singer.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonEncoding;
import com.singer.view.BlobDownloadView;
import com.singer.view.ExcelView;
import com.singer.view.FileDownloadView;

@Configuration
public class ViewConfigs {

	private final long TEN_META_BYTE = 10485760;

	@Bean(name = "viewNameResolver")
	public BeanNameViewResolver nameViewResolver() {
		BeanNameViewResolver nameViewResolver = new BeanNameViewResolver();
		nameViewResolver.setOrder(0);
		return nameViewResolver;
	}

	@Bean(name = "jsonView")
	public MappingJackson2JsonView jsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setEncoding(JsonEncoding.UTF8);
		jsonView.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return jsonView;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multiPartResolver() {
		CommonsMultipartResolver multiPartResolver = new CommonsMultipartResolver();
		multiPartResolver.setMaxUploadSize(TEN_META_BYTE);
		return multiPartResolver;
	}

	@Bean(name = "excelView")
	public ExcelView excelView() {
		return new ExcelView();
	}

	@Bean(name = "filedownloadView")
	public FileDownloadView fileDownloadView() {
		return new FileDownloadView();
	}

	@Bean(name = "blobdownloadView")
	public BlobDownloadView blobDownloadView() {
		return new BlobDownloadView();
	}

}
