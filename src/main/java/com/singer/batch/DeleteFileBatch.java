package com.singer.batch;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class DeleteFileBatch extends QuartzJobBean {

	private final Log log = LogFactory.getLog(DeleteFileBatch.class);

	// FTP다운 경로의 파일 삭제
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		ResourcePropertySource resourse;
		try {
			resourse = new ResourcePropertySource(new ClassPathResource("conf/property/global.properties"));
			String path = (String) resourse.getProperty("global.ftp.path");
			File deletePath = new File(path);
			Consumer<File> consumer = filePath -> {
				File[] files = filePath.listFiles();
				log.debug("There is " + files.length + " files in path");
				log.debug("file delete");
				for (File file : files) {
					file.delete();
				}
			};
			consumer.accept(deletePath);
		} catch (IOException e) {
			log.error(e, e);
		}

	}
}