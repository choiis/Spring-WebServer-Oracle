package com.singer.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");
		@Cleanup
		OutputStream out = res.getOutputStream();
		@Cleanup
		FileInputStream fis = new FileInputStream(file);

		FileCopyUtils.copy(fis, out);

	}

}
