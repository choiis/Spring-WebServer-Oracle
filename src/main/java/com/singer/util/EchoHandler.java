package com.singer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.singer.common.CommonUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RequestMapping("/chat")
public class EchoHandler extends TextWebSocketHandler {

	private static final Log logger = LogFactory.getLog(EchoHandler.class);

	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private Map<String, WebSocketSession> userMap = new HashedMap();

	protected String getUserName(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		Object obj = httpSession.get("username");
		if (obj != null) {
			String username = (String) obj;
			return username;
		} else {
			return null;
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		String username = getUserName(session);
		if (CommonUtil.isNull(username)) {
			return;
		}
		sessionList.add(session);
		userMap.put(username, session);
		logger.info("websocket connected open : " + session.getId() + " " + username);
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	enum direction {
		MSG("msg"), DM("dm");

		private final String direct;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		logger.info("websocket get text : " + session.getId() + " " + message.getPayload());
		String sessionName = getUserName(session);
		if (CommonUtil.isNull(sessionName)) {
			return;
		}

		String[] received = message.getPayload().split(",");

		if (direction.MSG.getDirect().equals(received[0])) {
			for (WebSocketSession webSocketSession : sessionList) {
				webSocketSession.sendMessage(new TextMessage(sessionName + " : " + received[1]));
			}
		} else if (direction.DM.getDirect().equals(received[0])) {
			WebSocketSession sendSession = userMap.get(received[1]);
			if (sendSession != null) {
				sendSession.sendMessage(new TextMessage(sessionName + "(귓속말) : " + received[2]));
			} else {
				session.sendMessage(new TextMessage(received[1] + " 님은 부재중 입니다"));
			}
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String username = getUserName(session);
		userMap.remove(username);
		sessionList.remove(session);
		logger.info("websocket connected close : " + session.getId() + " status " + status.getReason());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.error(session.getId() + " exception : " + exception.getMessage());
	}
}
