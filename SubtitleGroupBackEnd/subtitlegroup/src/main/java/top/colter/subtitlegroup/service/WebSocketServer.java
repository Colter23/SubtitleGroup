package top.colter.subtitlegroup.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import top.colter.subtitlegroup.entity.Sheet;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketServer extends TextWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SheetService sheetService;

    private static int onlineCount = 0;

    private static ConcurrentHashMap<String, WebSocketSession> webSocketMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String uid = session.getId();
        webSocketMap.put(uid, session);
        addOnlineCount();
        log.info("和客户端建立连接: "+uid);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        log.error("连接异常", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        if(webSocketMap.containsKey(session.getId())){
            webSocketMap.remove(session.getId());
            subOnlineCount();
        }
        log.info("和客户端断开连接");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取到客户端发送过来的消息
        String receiveMessage = message.getPayload();
        //log.info(receiveMessage);
        // 发送消息给客户端
        //解析发送的报文
        JSONObject jsonObject = JSON.parseObject(receiveMessage);

        log.info(receiveMessage);
        
        String action = jsonObject.getString("action");
        if ("update".equals(action)){
            Sheet sheet = jsonObject.getJSONObject("data").toJavaObject(Sheet.class);
            try {
                sheetService.updateSheet(sheet);
            }catch (Exception e){
                log.error(e.getMessage());
            }

        }else if ("insert".equals(action)){
            Sheet sheet = jsonObject.getJSONObject("data").toJavaObject(Sheet.class);
            sheetService.saveSheet(sheet);
        }

        for (WebSocketSession u: webSocketMap.values()) {
            if (u != session){
                try {
                    u.sendMessage(message);
                } catch(Exception e){
                    log.error("发送失败");
                }
            }
        }

        //session.sendMessage(new TextMessage(fakeAi(receiveMessage)));
        // 关闭连接
        // session.close(CloseStatus.NORMAL);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
