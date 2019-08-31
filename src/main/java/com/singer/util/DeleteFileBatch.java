package com.singer.util;

import java.io.File;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DeleteFileBatch extends QuartzJobBean {

	private final Log log = LogFactory.getLog(DeleteFileBatch.class);
	@Resource(name = "properties")
	private Properties properties;

	private String path;

	// FTP다운 경로의 파일 삭제
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		path = properties.getProperty("global.ftp.path");
		File deletePath = new File(path);
		File[] files = deletePath.listFiles();
		log.debug("There is " + files.length + " files in path");
		log.debug("file delete");
		for (File file : files) {
			file.delete();
		}
	}
}