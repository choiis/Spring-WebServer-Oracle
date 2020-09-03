package com.singer.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("streamController")
public class StreamController extends BaseController {

	private final Log log = LogFactory.getLog(StreamController.class);

	@Resource(name = "properties")
	private Properties properties;

	@RequestMapping(value = "/fileStreaming", method = RequestMethod.GET)
	public void fileStreaming(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String path = properties.getProperty("global.stream.path");
		File file = new File(path);

		RandomAccessFile randomFile = new RandomAccessFile(file, "r");

		long rangeStart = 0; // 요청 범위의 시작 위치
		long rangeEnd = 0; // 요청 범위의 끝 위치
		boolean isPart = false; // 부분 요청일 경우 true, 전체 요청의 경우 false

		try {
			long movieSize = randomFile.length();
			String range = request.getHeader("range");
			log.info("fileStreaming range " + range);
			if (range != null) {
				// 처리의 편의를 위해 요청 range에 end 값이 없을 경우 넣어줌
				if (range.endsWith("-")) {
					range = range + (movieSize - 1);
				}
				int idxm = range.trim().indexOf("-");
				rangeStart = Long.parseLong(range.substring(6, idxm));
				rangeEnd = Long.parseLong(range.substring(idxm + 1));
				if (rangeStart > 0) {
					isPart = true;
				}
			} else {
				rangeStart = 0;
				rangeEnd = movieSize - 1;
			} // 전송 파일 크기
			long partSize = rangeEnd - rangeStart + 1;

			// 전송시작
			response.reset(); // 전체 요청일 경우 200, 부분 요청일 경우 206을 반환상태 코드로 지정
			response.setStatus(isPart ? HttpStatus.PARTIAL_CONTENT.value() : HttpStatus.OK.value());
			log.info(isPart ? HttpStatus.PARTIAL_CONTENT.value() : HttpStatus.OK.value());
			// mime type 지정
			response.setContentType("video/mp4");
			// 전송 내용을 헤드에 넣어준다. 마지막에 파일 전체 크기를 넣는다.
			response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Length", "" + partSize);

			OutputStream out = response.getOutputStream();

			randomFile.seek(rangeStart);

			int bufferSize = 65536;
			byte[] buf = new byte[bufferSize];
			do {
				int block = partSize > bufferSize ? bufferSize : (int) partSize;
				int len = randomFile.read(buf, 0, block);
				out.write(buf, 0, len);
				partSize -= block;
			} while (partSize > 0);

		} catch (IOException e) {

		} finally {
			randomFile.close();
		}
	}

}
