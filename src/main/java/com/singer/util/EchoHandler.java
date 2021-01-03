package com.singer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequestMapping("/chat")
public class EchoHandler extends TextWebSocketHandler {

	private static final Log logger = LogFactory.getLog(EchoHandler.class);
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	protected String getUserName(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		Object obj = httpSession.get("username");
		if (obj != null) {
			String username = (String) obj;
			return username;
		} else {
			return "";
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		sessionList.add(session);
		logger.info("websocket connected open : " + session.getId() + " " + getUserName(session));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		logger.info("websocket get text : " + session.getId() + " " + message.getPayload());

		for (WebSocketSession webSocketSession : sessionList) {
			webSocketSession.sendMessage(new TextMessage(getUserName(session) + " : " + message.getPayload()));
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		logger.info("websocket connected close : " + session.getId());
	}
}
