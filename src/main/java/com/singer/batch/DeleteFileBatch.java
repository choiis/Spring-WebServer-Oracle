package com.singer.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
		Path deletePath = Paths.get(path);
		Consumer<Path> consumer = filePath -> {
			Stream<Path> stream;
			try {
				stream = Files.list(deletePath);
				Iterator<Path> files = stream.iterator();
				while (files.hasNext()) {
					Path file = files.next();
					log.debug(file.getFileName());
					if (!Files.isDirectory(file)) {
						Files.delete(file);
					}
				}
			} catch (IOException e) {
				log.error(e, e);
			}
		};
		consumer.accept(deletePath);

	}
}