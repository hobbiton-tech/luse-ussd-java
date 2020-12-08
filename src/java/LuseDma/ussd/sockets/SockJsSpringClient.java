/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LuseDma.ussd.sockets;
/**
 *
 * @author Chisha
 */
import java.util.Scanner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.messaging.converter.ByteArrayMessageConverter;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Stand alone WebSocketStompClient.
 *
 */
public class SockJsSpringClient {
    private static String URL = "ws://41.175.23.42:8484/api/v1/websocket";
    //private static String URL = "ws://localhost:8080/api/v1/websocket";//"ws://ef57782c36f0.ngrok.io/api/v1/websocket";
    //http://ef57782c36f0.ngrok.io 
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
        
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setMessageConverter(new ByteArrayMessageConverter());
        stompClient.setMessageConverter(new SimpleMessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        System.out.println("Connecting to websocket server");
        
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        //webSocketHttpHeaders.add("Authorization", "Bearer oUDDDeV3WdS6sdTDXqjxKlPuNE06IWxK-fjagPTvq5zPOykRMejTpiR8_cckFNIVyscf9uiItkoHtOpP");
        
        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("Authorization", "Bearer 1bcZeD073kGYnQWYBWTjwELCtZ3rb8zrgawTtURTiROcYzABEmLeKk1RYzIhhRaKgbK4rE1qnyOhDsju");
        //connectHeaders.add("Authorization", "Bearer SJoRCY_oHjSV8IdaW6zexTZf_i5YMm3aRHHoHar-Ui_UjVSSCP9HmN8ldWjWMsZAnwbFrbN5b2wB1RU8");
        stompClient.connect(URL, webSocketHttpHeaders , connectHeaders, sessionHandler);
        
        //stompClient.connect(URL, sessionHandler);
        new Scanner(System.in).nextLine(); // Don't close immediately.
    }
    /*public static  WsClient(String uri) throws Exception {

        ClientEndpointConfig.Builder configBuilder = ClientEndpointConfig.Builder.create();

        configBuilder.configurator(new ClientEndpointConfig.Configurator() {
            public void beforeRequest(Map<String, List<String>> headers) {
                headers.put("FriendlyName", Arrays.asList("TakaTurautin"));
            }
        });
        ClientEndpointConfig clientConfig = configBuilder.build();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        container.connectToServer(this, clientConfig, URI.create(uri));

    }*/
}
