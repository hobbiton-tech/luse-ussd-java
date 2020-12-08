/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LuseDma.ussd.handlers;
/**
 * @author Chisha
 */

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.riversun.promise.Func;
import org.riversun.promise.Promise;
import org.springframework.messaging.converter.ByteArrayMessageConverter;

import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Stand alone WebSocketStompClient.
 */
public class SockJsSpringClient {
    private static String URL = AppConfigHelper.LUSEDMA_WEBSOCKET_URL;

    public SockJsSpringClient() {

    }

    public WebSocketStompClient connectWebsocket(String action, JSONObject payload, MongoDBOld mongodb, String msisdn) {
        WebSocketStompClient stompClient=null;
        try {
            Logger logger = LogManager.getLogger(WebsocketSessionHandler.class);

            WebSocketClient client = new StandardWebSocketClient();

            stompClient = new WebSocketStompClient(client);
            stompClient.setMessageConverter(new ByteArrayMessageConverter());
            stompClient.setMessageConverter(new SimpleMessageConverter());

            StompSessionHandler sessionHandler = new WebsocketSessionHandler(action, payload, mongodb, msisdn);
            System.out.println("Connecting to websocket server");

            WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();

            StompHeaders connectHeaders = new StompHeaders();
            connectHeaders.add("Authorization", "Bearer " + AppConfigHelper.LUSEDMA_ACCESS_TOKEN);
            stompClient.connect(URL, webSocketHttpHeaders, connectHeaders, sessionHandler);

            if(action.equals("fundAccount")){
                Thread.sleep(20000);
            }
            else{
                Thread.sleep(10000);
            }
            System.out.println("closing websocket");
            stompClient.stop();
        } catch (Exception e) {
            System.out.println("error ==> " + e.getMessage());
        }
        return stompClient;
    }


}
