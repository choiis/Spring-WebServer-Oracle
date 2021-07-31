package com.singer.batch;

import java.io.File;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.singer.util.PropertyUtil;

import lombok.Setter;

@Setter
@Component
@DisallowConcurrentExecution
public class DeleteFileBatch extends QuartzJobBean {

	private final Log log = LogFactory.getLog(DeleteFileBatch.class);

	private PropertyUtil propertyUtil;

	// FTP다운 경로의 파일 삭제
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String path = propertyUtil.getS3FilePath();
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

	}
}