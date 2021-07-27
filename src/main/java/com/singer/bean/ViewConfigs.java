package com.singer.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.singer.view.BlobDownloadView;
import com.singer.view.ExcelView;
import com.singer.view.FileDownloadView;

@Configuration
public class ViewConfigs {

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
