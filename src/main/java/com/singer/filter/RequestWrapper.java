package com.singer.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.singer.common.AppException;

public class RequestWrapper extends HttpServletRequestWrapper {

	private final Log log = LogFactory.getLog(RequestWrapper.class);

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		log.info("RequestWrapper init");
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {

			// SQL Injection 대처
			try {
				B2CConfig b2cConfig = new B2CConfig();
				List<String> skiplist = b2cConfig.getSqlInjectionSkipParam();

				if (!skiplist.contains(parameter)) {
					List<String> blackListArray = b2cConfig.getSqlInjectionBlackList();
					if (blackListArray != null) {
						String val = values[i].toLowerCase();
						for (int j = 0; j < blackListArray.size(); j++) {
							String black = blackListArray.get(j);

							if (val.indexOf(black) != -1) {
								throw new AppException("SQL Injection 위험이 있습니다 ");
							}
						}
					}
				}
			} catch (ParserConfigurationException e) {
				log.info("ParserConfigurationException");
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
