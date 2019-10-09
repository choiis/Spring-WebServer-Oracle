package com.singer.common;

import java.util.List;

public class CommonUtil {

	/**
	 * 배열 길이를 반환한다
	 */
	public static int getLength(Object[] array) {
		return array.length;
	}

	/**
	 * 배열 길이를 반환한다
	 */
	public static int getLength(List<?> array) {
		if (isNull(array)) {
			return 0;
		} else {
			return array.size();
		}
	}

	/**
	 * 배열 길이를 반환한다
	 */
	public static int getLength(int[] array) {
		return array.length;
	}

	/**
	 * 배열 길이를 반환한다
	 */
	public static int getLength(long[] array) {
		return array.length;
	}

	/**
	 * 배열 길이를 반환한다
	 */
	public static int getLength(char[] array) {
		return array.length;
	}

	/**
	 * 배열 길이를 반환한다
	 */
	public static int getLength(String[] array) {
		return array.length;
	}

	/**
	 * 널 체크
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 널 체크
	 */
	public static boolean isNull(String obj) {
		if (obj == null) {
			return true;
		} else if ("".equals(obj.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 널 체크
	 */
	public static boolean isNull(int obj) {
		if (obj == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 널 체크
	 */
	public static boolean isNull(long obj) {
		if (obj == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Oracle nvl
	 */
	public static String nvl(String str, String nvl) {
		if (isNull(str)) {
			return nvl;
		} else {
			return str;
		}
	}

	/**
	 * Oracle nvl
	 */
	public static Object nvl(Object obj, String nvl) {
		if (isNull(obj)) {
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
}
