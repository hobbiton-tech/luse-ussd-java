package LuseDma.ussd.handlers;

import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.pojos.luse.SocketResponse;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

public class WebsocketSessionHandler extends StompSessionHandlerAdapter {

    private String action;
    private JSONObject payload;
    private String requestResponse;
    private MongoDBOld mongodb;
    private String msisdn;

    public WebsocketSessionHandler(String action, JSONObject payload, MongoDBOld mongodb, String msisdn){
        this.action = action;
        this.payload = payload;
        this.mongodb = mongodb;
        this.msisdn = msisdn;
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        if (throwable instanceof ConnectionLostException) {
            // if connection lost, call this
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception");
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String received = new String((byte[]) payload);
        System.out.println("StompHeaders : " + new Gson().toJson(headers));
        System.out.println("Received : " + received);

        Gson json = new Gson();
        SocketResponse response = json.fromJson(received, SocketResponse.class);

        switch (response.getCode()){
            case "99": case "8": case "0":
                LuseServiceCenter.sendSMS(this.msisdn, response.getMessage());
                break;
        }

    }



    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        System.out.println("action == > "+this.action);
        System.out.println("payload == > "+this.payload);
        switch (this.action){
            case "orderSecurity":
                this.buySecurity(session, this.payload);
                break;
            case "fundAccount":
                this.fundAccount(session, this.payload);
                break;
        }
    }


    private void buySecurity(StompSession session, JSONObject payload) {
        session.subscribe("/session/trading/order-result", this);
        session.subscribe("/session/validation/errors", this);
        session.subscribe("/session/exceptions/errors", this);

        System.out.println("Subscribed to /trading/order-result");
        System.out.println("Subscribed to /session/validation/errors");
        System.out.println("Subscribed to /session/exceptions/errors");

        session.send("/live/trading/place-order", new Gson().toJson(payload).getBytes());
        System.out.println("Sent to '/live/trading/place-order' => " + payload);
    }

    private void fundAccount(StompSession session, JSONObject payload) {
        ///wallets/deposit-result
        session.subscribe("/session/wallets/deposit-result", this);
        session.subscribe("/session/validation/errors", this);
        session.subscribe("/session/exceptions/errors", this);

        System.out.println("Subscribed to /wallets/deposit-result");
        System.out.println("Subscribed to /session/validation/errors");
        System.out.println("Subscribed to /session/exceptions/errors");

        session.send("/live/wallets/deposit", new Gson().toJson(payload).getBytes());
        System.out.println("Sent to '/live/wallets/deposit' => " + payload);
    }

}
