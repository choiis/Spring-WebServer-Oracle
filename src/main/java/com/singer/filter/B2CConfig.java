package com.singer.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class B2CConfig {

	private final Log log = LogFactory.getLog(B2CConfig.class);

	private static B2CConfig b2cConfig = new B2CConfig();

	public static B2CConfig getInstance() {
		return b2cConfig;
	}

	List<String> skipParam;
	List<String> blackList;

	private B2CConfig() {
		super();
		loadConfigXml();
		log.info("B2CConfig Singleton Created");
	}

	private void loadConfigXml() {
		skipParam = new ArrayList<>();
		skipParam.add("action");
		skipParam.add("javax");
		skipParam.add("user_id");
		skipParam.add("email");
		blackList = new ArrayList<>();
		blackList.add("alter");
		blackList.add("delete");
		blackList.add("drop");
		blackList.add("begin");
		blackList.add("cursor");
		blackList.add("create");
		blackList.add("declare");
		blackList.add("1=1");
	}

	public List<String> getSqlInjectionSkipParam() {
		return skipParam;
	}

	public List<String> getSqlInjectionBlackList() {

		return blackList;
	}
}
