package com.singer.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestWrapper extends HttpServletRequestWrapper {

	private final Log log = LogFactory.getLog(RequestWrapper.class);

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		log.info("RequestWrapper init");
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
