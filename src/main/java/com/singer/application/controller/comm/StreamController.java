package com.singer.application.controller.comm;

import com.singer.application.controller.BaseController;
import com.singer.infrastructure.config.S3Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api/v1/comm")
@RestController
@Slf4j
public class StreamController extends BaseController {

	@Autowired
	private S3Properties s3Properties;

	@RequestMapping(value = "/videoStreaming", method = RequestMethod.GET)
	public ResponseEntity<ResourceRegion> videoStreaming(@RequestHeader HttpHeaders headers) throws IOException {
		log.info("videoStreaming!!!");

		String streamurl = s3Properties.streamPath();
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