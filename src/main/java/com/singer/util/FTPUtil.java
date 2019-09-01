package com.singer.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FTPUtil {

	private final Log log = LogFactory.getLog(FTPUtil.class);

	@Resource(name = "properties")
	private Properties properties;

	private String ftpIp; // FTP 가상머신 서버의 ip

	private int port;

	private String username;

	private String password;

	private String path;

	@PostConstruct
	private void init() {
		ftpIp = properties.getProperty("global.ftp.server");
		port = Integer.parseInt(properties.getProperty("global.ftp.port"));
		username = properties.getProperty("global.ftp.username");
		password = properties.getProperty("global.ftp.password");
		path = properties.getProperty("global.ftp.path");
	}

	private FTPUtil() {

	}

	private FTPClient initFTPClient() {
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("UTF-8");
		try {

			ftp.connect(ftpIp, port);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.debug("FTP Server refused");
				System.exit(1);
			}

			if (!ftp.login(username, password)) {
				ftp.logout();
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
		} catch (IOException e) {

		}
		return ftp;
	}

	private void closeFTPClient(FTPClient ftp) {
		if (ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException e) {
			}
		}
	}

	public File downFile(String fileName) {
		FTPClient ftp = initFTPClient();
		File file = new File(path, fileName);
		BufferedOutputStream os = null;

		try {

			os = new BufferedOutputStream(new FileOutputStream(file));
			ftp.retrieveFile(fileName, os);
		} catch (IOException e) {

		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			closeFTPClient(ftp);
		}
		return file;
	}

	public boolean sendFile(String fileName, MultipartFile file) {
		boolean isSuccess = false;
		FTPClient ftp = initFTPClient();
		InputStream is = null;

		try {
			is = file.getInputStream();
			isSuccess = ftp.storeFile(fileName, is);

		} catch (IOException e) {
			isSuccess = false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			closeFTPClient(ftp);
		}
		return isSuccess;
	}

	public boolean deleteFile(String fileName) {

		boolean isSuccess = false;
		FTPClient ftp = initFTPClient();

		try {
			isSuccess = ftp.deleteFile(fileName);
		} catch (IOException e) {
			
		} finally {
			closeFTPClient(ftp);
		}
		return isSuccess;
	}

}