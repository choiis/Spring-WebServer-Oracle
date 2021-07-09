package com.singer.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ContentDisposition;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import lombok.Cleanup;

public class FileDownloadView extends AbstractView {
	public FileDownloadView() {

		setContentType("apllication/download; charset=utf-8");

	}

	@SuppressWarnings("unchecked")
	@Override

	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		HashMap<String, Object> downloadFile = (HashMap<String, Object>) model.get("downloadFile");

		File file = (File) downloadFile.get("downfile");
		String fileName = (String) downloadFile.get("filename");

		res.setContentType(getContentType());
		res.setContentLength((int) file.length());
		String contentDisposition = ContentDisposition.builder("attachment").filename(fileName, StandardCharsets.UTF_8)
				.build().toString();
		res.setHeader("Content-Disposition", contentDisposition);
		res.setHeader("Content-Transfer-Encoding", "binary");
		@Cleanup
		OutputStream out = res.getOutputStream();
		@Cleanup
		FileInputStream fis = new FileInputStream(file);

		FileCopyUtils.copy(fis, out);

	}

}
