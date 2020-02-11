package com.singer.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;

import lombok.Cleanup;

public class RequestWrapper extends HttpServletRequestWrapper {

	private final Log log = LogFactory.getLog(RequestWrapper.class);
	private byte[] bytes;

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		log.info("RequestWrapper init ");
		if ("application/json;charset=UTF-8".equals(request.getContentType())) { // @RequestBody요청
			bytes = new String(getBody(request)).getBytes();

		}
	}

	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		return new ServletInputStreamImpl(bis);
	}

	class ServletInputStreamImpl extends ServletInputStream {

		private InputStream is;

		public ServletInputStreamImpl(InputStream bis) {
			is = bis;
		}

		public int read() throws IOException {
			return is.read();
		}

		public int read(byte[] b) throws IOException {
			return is.read(b);
		}

		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {
			// TODO Auto-generated method stub

		}

	}

	public String getBody(HttpServletRequest request) {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			@Cleanup
			InputStream is = request.getInputStream();
			if (is != null) {
				@Cleanup
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				char[] charBuffer = new char[1024];
				int bytesRead = -1;
				while ((bytesRead = br.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {

		}

		body = cleanXSS(stringBuilder.toString());
		return body;
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);

			try {
				B2CConfig b2cConfig = B2CConfig.getInstance();
				List<String> skiplist = b2cConfig.getSqlInjectionSkipParam();

				if (!skiplist.contains(parameter)) {
					List<String> blackListArray = b2cConfig.getSqlInjectionBlackList();
					if (blackListArray != null) {
						String val = encodedValues[i].toLowerCase();

						int size = blackListArray.size();
						for (int j = 0; j < size; j++) {
							String black = blackListArray.get(j);

							if (val.indexOf(black) != -1) {
								throw new AppException(ExceptionMsg.EXT_MSG_SECURE_1);
							}
						}
						encodedValues[i] = val;
					}
				}
			} catch (AppException e) {
				log.info("AppException");
			}

		}

		return encodedValues;

	}

	public String getParameter(String name) {

		String value = super.getParameter(name);

		if (value == null) {
			return null;
		} else if ("".equals(value)) {
			return value;
		}

		return cleanXSS(value);
	}

	public String getHeader(String name) {

		String value = super.getHeader(name);

		if (value == null) {
			return null;
		}

		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		value = value.replaceAll("<", "&lt;");
		value = value.replaceAll(">", "&gt;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}
