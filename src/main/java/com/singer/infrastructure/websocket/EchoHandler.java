package com.singer.infrastructure.websocket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.singer.common.util.DateUtil;
import com.singer.infrastructure.util.S3Util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoHandler extends TextWebSocketHandler {


    private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
    private Map<String, WebSocketSession> userMap = new HashedMap<>();

    private static final String FILE_UPLOAD_PATH = "D:/tmp/";

    private static final String PACKET_HEADER_NAME = "N";
    private static final String PACKET_HEADER_DM = "D";
    private static final String PACKET_HEADER_LIST = "L";
    private static final String PACKET_HEADER_ALL = "A";

    private static final String S3_FILE_PATH = "chatupload/";

    private static final String JPG_EXTENSION = ".jpg";

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    enum direction {
        MSG("msg"), DM("dm"), LIST("list");

        private final String direct;
    }

    @Autowired
    private S3Util s3Util;

    protected String getUserName(WebSocketSession session) {
        Map<String, Object> httpSession = session.getAttributes();
        Optional<Object> obj = Optional.of(httpSession.get("username"));
        if (obj.isPresent()) {
            String username = (String) obj.get();
            return username;
        } else {
            return null;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username = getUserName(session);
        if (StringUtils.isEmpty(username)) {
            return;
        }
        sessionList.add(session);
        userMap.put(username, session);
        log.info("websocket connected open : " + session.getId() + " " + username);
        StringBuilder nameList = new StringBuilder("");
        Stream<String> stream = userMap.keySet().stream();
        stream.forEach(x -> {
            nameList.append(getUserName(userMap.get(x)));
            nameList.append("<br>");
        });

        for (WebSocketSession webSocketSession : sessionList) {
            webSocketSession.sendMessage(new TextMessage(PACKET_HEADER_LIST + nameList.toString()));
        }
        session.sendMessage(new TextMessage(PACKET_HEADER_NAME + username));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    	log.info("websocket get text : " + session.getId() + " " + message.getPayload());
        String sessionName = getUserName(session);
        if (StringUtils.isEmpty(sessionName)) {
            return;
        }

        Optional<String> optional = Optional.of(message.getPayload());
        if (!optional.isPresent()) {
            return;
        }
        String[] received = optional.get().split(",");

        if (StringUtils.equals(direction.MSG.getDirect(), received[0])) {
            for (WebSocketSession webSocketSession : sessionList) {
                webSocketSession.sendMessage(new TextMessage(PACKET_HEADER_ALL + sessionName + " : " + received[1]));
            }

        } else if (StringUtils.equals(direction.DM.getDirect(), received[0])) {
            WebSocketSession sendSession = userMap.get(received[1]);
            if (sendSession != null) {
                sendSession.sendMessage(new TextMessage(PACKET_HEADER_DM + sessionName + "(dm) : " + received[2]));
            } else {
                session.sendMessage(new TextMessage(PACKET_HEADER_DM + received[1] + " �떂�씠 �뾾�뒿�땲�떎"));
            }
        }

    }

    private void filelogSend(String datetime, String name, File uploadFile, String address) {

        String uploadFilename = S3_FILE_PATH + datetime + JPG_EXTENSION;
        s3Util.putS3File(uploadFilename, uploadFile);

    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        ByteBuffer byteBuffer = message.getPayload();

        String datetime = DateUtil.getTodayTime();
        String tempFileName = datetime + JPG_EXTENSION;
        File dir = new File(FILE_UPLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File imgFile = new File(FILE_UPLOAD_PATH, tempFileName);

        try {
            @Cleanup
            FileOutputStream fos = new FileOutputStream(imgFile);
            @Cleanup
            FileChannel fileChannel = fos.getChannel();
            byteBuffer.compact();
            fileChannel.write(byteBuffer);
            byteBuffer.position(0);

            for (WebSocketSession webSocketSession : sessionList) {
                webSocketSession.sendMessage(new BinaryMessage(byteBuffer));
            }
            fileChannel.write(byteBuffer);
            filelogSend(datetime, getUserName(session), imgFile,
                session.getRemoteAddress().getAddress().getHostAddress());

            imgFile.delete();
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = getUserName(session);
        userMap.remove(username);
        sessionList.remove(session);
        log.info("websocket connected close : " + session.getId() + " name " + username + " status "
            + status.getReason());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	log.error(session.getId() + " exception : " + exception.getMessage());
    }
}
