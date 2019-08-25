package com.singer.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DeleteFileBatch extends QuartzJobBean {

	private final Log log = LogFactory.getLog(DeleteFileBatch.class);

	private String path = "C://downFTP";

	// FTP다운 경로의 파일 삭제
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		File deletePath = new File(path);
		File[] files = deletePath.listFiles();
		log.debug("There is " + files.length + " files in path");
		log.debug("file delete");
		for (File file : files) {
			file.delete();
		}
	}
}