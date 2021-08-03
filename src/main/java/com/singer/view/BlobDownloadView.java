/**
 * 
 */
package com.singer.view;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import lombok.Cleanup;
import oracle.sql.BLOB;

public class BlobDownloadView extends AbstractView {
	public BlobDownloadView() {
		// setContentType("apllication/download; charset=utf-8");
		setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		HashMap<String, Object> downloadFile = (HashMap<String, Object>) model.get("downloadFile");
		BLOB fileblob = (BLOB) Optional.of(downloadFile.get("fileblob")).orElseThrow(IllegalArgumentException::new);
		String fileName = (String) Optional.of(downloadFile.get("filename")).orElseThrow(IllegalArgumentException::new);

		res.setContentType(getContentType());
		res.setContentLength((int) fileblob.length());
		String contentDisposition = ContentDisposition.builder("attachment").filename(fileName, StandardCharsets.UTF_8)
				.build().toString();
		res.addHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		res.addHeader(HttpHeaders.TRANSFER_ENCODING, "binary");
		@Cleanup
		OutputStream out = res.getOutputStream();

		FileCopyUtils.copy(fileblob.getBinaryStream(), out);

	}
}
