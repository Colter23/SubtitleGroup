package top.colter.subtitlegroup.service;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;


public class QQWebSocketClient extends WebSocketClient {



    private static String session = "";
    private static final Log log = LogFactory.get();

    public QQWebSocketClient(URI serverUri) {
        super(serverUri);
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("连接到服务端");
    }

    @Override
    public void onMessage(String s) {
        log.info("收到消息: "+s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("关闭连接"+s);
    }

    @Override
    public void onError(Exception e) {
        log.info("出错拉");
    }


}
