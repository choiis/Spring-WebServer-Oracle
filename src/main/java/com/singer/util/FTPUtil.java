package com.singer.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

public class FTPUtil {

	private final Log log = LogFactory.getLog(FTPUtil.class);

	private static final FTPUtil INSTANCE = new FTPUtil();

	private FTPClient ftp;

	private String ftpIp = "127.0.0.1"; // FTP 가상머신 서버의 ip

	private int port = 21;

	private String id = "FTPClient";

	private String password = "qw1324..";

	private FTPUtil() {

		ftp = new FTPClient();
		ftp.setControlEncoding("UTF-8");
		try {

			ftp.connect(ftpIp, port);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.debug("FTP Server refused");
				System.exit(1);
			}

			if (!ftp.login(id, password)) {
				ftp.logout();
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

		} catch (IOException e) {
			// TODO: handle exception
		}

	}

	public static FTPUtil getInstance() {
		return INSTANCE;
	}

	public File downFile(String fileName) {
		File file = new File(fileName);
		BufferedOutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(file));
			ftp.retrieveFile(fileName, os);

		} catch (IOException e) {
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e2) {
				}
			}
		}
		return file;
	}

	public boolean sendFile(String fileName, MultipartFile file) {
		boolean isSuccess = false;

		InputStream is = null;
		try {
			is = file.getInputStream();
			isSuccess = ftp.storeFile(fileName, is);

		} catch (IOException e) {
			e.printStackTrace();
			isSuccess = false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return isSuccess;
	}

	public boolean deleteFile(String fileName) {

		boolean isSuccess = false;
		try {
			isSuccess = ftp.deleteFile(fileName);
		} catch (IOException e) {

		}
		return isSuccess;
	}

}
