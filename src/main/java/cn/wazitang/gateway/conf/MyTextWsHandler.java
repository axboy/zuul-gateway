package cn.wazitang.gateway.conf;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者 zcw
 * 时间 2017/8/18 23:06
 * 描述 TODO
 */
@Component
public class MyTextWsHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionList.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session);
    }

    /**
     * 发送给所有人
     *
     * @param message;
     */
    public void sendToAll(Object message) {
        byte[] msg = new Gson().toJson(message).getBytes();
        sessionList.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
