package com.singer.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import com.singer.common.Constants.BROWSER_CODE;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

	/**
	 * Oracle nvl
	 */
	public static String nvl(String str, String nvl) {
		if (StringUtils.isEmpty(str)) {
			return nvl;
		} else {
			return str;
		}
	}

	/**
	 * Oracle nvl
	 */
	public static Object nvl(Object obj, String nvl) {
		if (ObjectUtils.isEmpty(obj)) {
			return nvl;
		} else {
			return obj;
		}
	}

	/**
	 * 페이지 카운트 반환
	 */
	public static int getPageCnt(int cnt) {
		return 1 + (cnt - 1) / Constants.ROW_PER_PAGE;
	}

	/**
	 * 첨부문서 파일 확인
	 */
	public static boolean chkDOCFile(String fileName) throws Exception {
		if (fileName == null || "".equals(fileName))
			throw new Exception("NullFileException");
		if (fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".docx")
				|| fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx")
				|| fileName.toLowerCase().endsWith(".csv") || fileName.toLowerCase().endsWith(".pdf")
				|| fileName.toLowerCase().endsWith(".hwp"))
			return true;
		else
			return false;
	}

	/**
	 * 이미지 파일 확인
	 */
	public static boolean chkIMGFile(String fileName) throws Exception {
		if (fileName == null || "".equals(fileName))
			throw new Exception("NullFileException");
		if (fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".bmp")
				|| fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")
				|| fileName.toLowerCase().endsWith(".jpe") || fileName.toLowerCase().endsWith(".jfif")
				|| fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".tif")
				|| fileName.toLowerCase().endsWith(".tiff"))
			return true;
		else
			return false;
	}

	/**
	 * 동영상 파일 확인
	 */
	public static boolean chkVideoFile(String fileName) throws Exception {
		if (fileName == null || "".equals(fileName))
			throw new Exception("NullFileException");
		if (fileName.toLowerCase().endsWith(".avi") || fileName.toLowerCase().endsWith(".mp4")
				|| fileName.toLowerCase().endsWith(".wmv") || fileName.toLowerCase().endsWith(".asf")
				|| fileName.toLowerCase().endsWith(".mpe") || fileName.toLowerCase().endsWith(".asx")
				|| fileName.toLowerCase().endsWith(".mpeg") || fileName.toLowerCase().endsWith(".rm")
				|| fileName.toLowerCase().endsWith(".mov") || fileName.toLowerCase().endsWith(".dat")
				|| fileName.toLowerCase().endsWith(".flv"))
			return true;
		else
			return false;
	}

	/**
	 * 오디오 파일 확인
	 */
	public static boolean chkAudioFile(String fileName) throws Exception {
		if (fileName == null || "".equals(fileName))
			throw new Exception("NullFileException");
		if (fileName.toLowerCase().endsWith(".mp3") || fileName.toLowerCase().endsWith(".ogg")
				|| fileName.toLowerCase().endsWith(".wma") || fileName.toLowerCase().endsWith(".wav")
				|| fileName.toLowerCase().endsWith(".au") || fileName.toLowerCase().endsWith(".rm")
				|| fileName.toLowerCase().endsWith(".mid"))
			return true;
		else
			return false;
	}

	/**
	 * 파일명 확장자 추출
	 */
	public static String getExtensionName(String fileName) {
		int idx = fileName.lastIndexOf(".");
		if (idx != -1) {
			String ext = fileName.substring(idx + 1);
			return ext;
		} else {
			return null;
		}
	}

	/**
	 * request에서 IPv4리턴
	 */
	public static String getIp(HttpServletRequest request) {

		// VM arguments 에 -Djava.net.preferIPv4Stack=true
		// IPv6 => IPv4
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;

	}

	public static BROWSER_CODE getBrower(HttpServletRequest request) {
		String browserInfo = request.getHeader("User-Agent");

		if (browserInfo.indexOf("Chrome") > -1) {
			return BROWSER_CODE.CHROME;
		} else if (browserInfo.indexOf("Trident") > -1 || browserInfo.indexOf("MSIE") > -1) {
			return BROWSER_CODE.IE;
		} else if (browserInfo.indexOf("Whale") > -1) {
			return BROWSER_CODE.WHALE;
		} else if (browserInfo.indexOf("Opera") > -1 || browserInfo.indexOf("OPR") > -1) {
			return BROWSER_CODE.OPERA;
		} else if (browserInfo.indexOf("Firefox") > -1) { // 파이어폭스
			return BROWSER_CODE.FIREFOX;
		} else if (browserInfo.indexOf("Safari") > -1) {
			return BROWSER_CODE.SAFARI;
		} else if (browserInfo.indexOf("iPhone") > -1) { // 파이어폭스
			return BROWSER_CODE.IPHONE;
		} else if (browserInfo.indexOf("Android") > -1) {
			return BROWSER_CODE.ANDROID;
		} else {
			return null;
		}
	}
}
