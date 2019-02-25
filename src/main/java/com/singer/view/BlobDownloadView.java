/**
 * 
 */
package com.singer.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import oracle.sql.BLOB;

public class BlobDownloadView extends AbstractView {
	public BlobDownloadView() {
		setContentType("apllication/download; charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		HashMap<String, Object> downloadFile = (HashMap<String, Object>) model.get("downloadFile");
		BLOB fileblob = (BLOB) downloadFile.get("fileblob");
		String fileName = (String) downloadFile.get("filename");

		res.setContentType(getContentType());
		res.setContentLength((int) fileblob.length());
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = res.getOutputStream();
		try {
			FileCopyUtils.copy(fileblob.getBinaryStream(), out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
