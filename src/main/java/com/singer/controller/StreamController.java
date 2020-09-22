package com.singer.controller;

import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("streamController")
public class StreamController extends BaseController {

	private final Log log = LogFactory.getLog(StreamController.class);

	@Resource(name = "properties")
	private Properties properties;

	@RequestMapping(value = "/videoStreaming", method = RequestMethod.GET)
	public ResponseEntity<ResourceRegion> videoStreaming(@RequestHeader HttpHeaders headers) throws IOException {
		log.info("videoStreaming!!!");

		String streamurl = properties.getProperty("global.stream.path");
		UrlResource video = new UrlResource(streamurl);
		ResourceRegion region = getResourceRegion(video, headers);

		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(region);
	}

	private ResourceRegion getResourceRegion(UrlResource video, HttpHeaders headers) throws IOException {
		final long chunkSize = 1000000L;
		long contentLength = video.contentLength();
		HttpRange httpRange = headers.getRange().stream().findFirst().get();
		if (httpRange != null) {
			long start = httpRange.getRangeStart(contentLength);
			long end = httpRange.getRangeEnd(contentLength);
			long rangeLength = Long.min(chunkSize, end - start + 1);
			return new ResourceRegion(video, start, rangeLength);
		} else {
			long rangeLength = Long.min(chunkSize, contentLength);
			return new ResourceRegion(video, 0, rangeLength);
		}
	}

}
