package com.singer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
@ServerEndpoint(value = "/chatsocket")
public class WebSocketHandler extends TextWebSocketHandler {

	private static final List<Session> sessionList = new ArrayList<>();
	private static final Log logger = LogFactory.getLog(WebSocketHandler.class);

	public WebSocketHandler() {
	}

	@OnOpen
	public void onOpen(Session session) {
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText(" join");
		} catch (IOException e) {
			logger.error(e, e);
		}
		sessionList.add(session);
	}

	private void sendAllSessionToMessage(Session ses, String message) {
		try {
			for (Session session : sessionList) {
				if (!session.getId().equals(ses.getId())) {
					session.getBasicRemote().sendText(message);
				}
			}
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			final Basic basic = session.getBasicRemote();
			// String msg = username + " : " + message;
			basic.sendText(message);
			sendAllSessionToMessage(session, message);
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	@OnError
	public void onError(Throwable e, Session session) {

	}

	@OnClose
	public void onClose(Session session) {
		logger.info("Session " + session.getId() + " has ended");
		sessionList.remove(session);
	}
}
