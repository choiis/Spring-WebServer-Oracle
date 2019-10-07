package com.singer.filter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.singer.exception.AppException;
import com.singer.util.XmlUtil;

public class B2CConfig extends XmlUtil {

	private final Log log = LogFactory.getLog(B2CConfig.class);

	private static B2CConfig b2cConfig = new B2CConfig();

	public static B2CConfig getInstance() {
		return b2cConfig;
	}

	private B2CConfig() {
		super();
		loadConfigXml();
		log.info("B2CConfig Singleton Created");
	}

	private void loadConfigXml() {
		InputStream is = B2CConfig.class.getResourceAsStream("/conf/b2c-config.xml");

		try {
			super.parse(is);
		} catch (SAXException e) {
			log.info("SAXException");
		} catch (AppException e) {
			log.info("AppException");
		}
	}

	public List<String> getSqlInjectionSkipParam() {
		List<String> list = new ArrayList<String>();
		String[] skipArr = super.getNodeList("sql-skip-param").split(",");
		for (int i = 0; i < skipArr.length; i++) {
			list.add(skipArr[i]);
		}
		return list;
	}

	public List<String> getSqlInjectionBlackList() {
		List<String> list = new ArrayList<String>();
		String[] skipArr = super.getNodeList("sql-injection-blacklist").split(",");
		for (int i = 0; i < skipArr.length; i++) {
			list.add(skipArr[i]);
		}
		return list;
	}
}
