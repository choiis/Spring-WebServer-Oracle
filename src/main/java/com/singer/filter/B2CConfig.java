package com.singer.filter;

import java.io.InputStream;

public class B2CConfig {

	private void loadConfigXml() {
		InputStream is = B2CConfig.class.getResourceAsStream("/conf/b2c-config.xml");
	}
}
