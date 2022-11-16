package com.singer.infrastructure.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.singer.infrastructure.config.S3Properties;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Component
@DisallowConcurrentExecution
@Slf4j
public class DeleteFileBatch extends QuartzJobBean {

	@Autowired
	private S3Properties s3Properties;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String path = s3Properties.getTempPath();
		Path deletePath = Paths.get(path);
		Consumer<Path> consumer = filePath -> {
			Stream<Path> stream;
			try {
				stream = Files.list(deletePath);
				Iterator<Path> files = stream.iterator();
				while (files.hasNext()) {
					Path file = files.next();
					log.debug("deleted file name " + file.getFileName());
					if (!Files.isDirectory(file)) {
						Files.delete(file);
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		};
		consumer.accept(deletePath);

	}
}
