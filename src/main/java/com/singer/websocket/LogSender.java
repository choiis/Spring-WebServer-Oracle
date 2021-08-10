package com.singer.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.singer.common.DateUtil;
import com.singer.service.SL01Service;
import com.singer.vo.SL01Vo;

@Component
@EnableAsync
public class LogSender {

	private static final Log logger = LogFactory.getLog(LogSender.class);

	@Autowired
	private SL01Service sl01Service;

	@Async("threadPoolTaskExecutor")
	public void chatlogInsert(SL01Vo sl01Vo) {
		try {
			sl01Vo.setLogdate(DateUtil.getTodayTime());
			sl01Service.insertChatLog(sl01Vo);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
