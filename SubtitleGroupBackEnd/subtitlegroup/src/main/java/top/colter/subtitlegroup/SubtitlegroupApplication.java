package top.colter.subtitlegroup;

import org.java_websocket.client.WebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import top.colter.subtitlegroup.service.QQWebSocketClient;

import java.net.URI;

@SpringBootApplication
public class SubtitlegroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubtitlegroupApplication.class, args);
    }

    //private static final String verifyKey = "INITKEYWaMMDxKf";
    //private static final String host = "127.0.0.1:8082";
    //@Bean
    //public WebSocketClient webSocketClient(){
    //    try {
    //        QQWebSocketClient client = new QQWebSocketClient(new URI("ws://"+host+"/message?verifyKey="+verifyKey));
    //        client.connect();
    //        return client;
    //    }catch (Exception e){
    //        return null;
    //    }
    //}

}
