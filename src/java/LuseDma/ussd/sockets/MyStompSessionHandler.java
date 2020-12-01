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
import com.google.gson.Gson;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.HashMap;
//import lusecsdintegration.BrivoHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.messaging.simp.stomp.ConnectionLostException;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and 
 * send a sample message to server.
 * 
 * @author Kalyan
 *
 */
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
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
        System.out.println("StompHeaders : " +new Gson().toJson(headers));
        System.out.println("Received : " +received);
    }
    
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        //doRegistration(session);
//        doBuyOrder(session);
        //doSellOrder(session);
        //doWalletDeposit(session);
        //doBrokerLinkage(session);
    }

//    private void doRegistration(StompSession session) {
//        Subscriber subscriber = getSubscriber();
//
//        session.subscribe("/subscribers/register-result/" + subscriber.asyncHashIdentifier, this);
//        session.subscribe("/session/validation/errors", this);
//        session.subscribe("/session/exceptions/errors", this);
//
//        System.out.println("Subscribed to /subscribers/register-result");
//        System.out.println("Subscribed to /session/validation/errors");
//        System.out.println("Subscribed to /session/exceptions/errors");
//
//        session.send("/live/subscribers/register/" + subscriber.asyncHashIdentifier, new Gson().toJson(subscriber).getBytes());
//        //System.out.println("Sent to '/live/subscribers/register/" + subscriber.asyncHashIdentifier+"' => " + new Gson().toJson(subscriber));
//    }
//    private void doBuyOrder(StompSession session) {
//        Order order = getBuyOrder();
//        session.subscribe("/session/trading/order-result", this);
//        session.subscribe("/session/validation/errors", this);
//        session.subscribe("/session/exceptions/errors", this);
//
//        System.out.println("Subscribed to /trading/order-result");
//        System.out.println("Subscribed to /session/validation/errors");
//        System.out.println("Subscribed to /session/exceptions/errors");
//
//        session.send("/live/trading/place-order", new Gson().toJson(order).getBytes());
//        System.out.println("Sent to '/live/trading/place-order' => " + new Gson().toJson(order));
//    }
//    private void doSellOrder(StompSession session) {
//        Order order = getSellOrder();
//        session.subscribe("/session/trading/order-result", this);
//        session.subscribe("/session/validation/errors", this);
//        session.subscribe("/session/exceptions/errors", this);
//
//        System.out.println("Subscribed to /trading/order-result");
//        System.out.println("Subscribed to /session/validation/errors");
//        System.out.println("Subscribed to /session/exceptions/errors");
//
//        session.send("/live/trading/place-order", new Gson().toJson(order).getBytes());
//        System.out.println("Sent to '/live/trading/place-order' => " + new Gson().toJson(order));
//    }
//    private void doWalletDeposit(StompSession session) {
//        WalletTransaction trxn = getTrxn();
//        ///wallets/deposit-result
//        session.subscribe("/session/wallets/deposit-result", this);
//        session.subscribe("/session/validation/errors", this);
//        session.subscribe("/session/exceptions/errors", this);
//
//        System.out.println("Subscribed to /wallets/deposit-result");
//        System.out.println("Subscribed to /session/validation/errors");
//        System.out.println("Subscribed to /session/exceptions/errors");
//
//        session.send("/live/wallets/deposit", new Gson().toJson(trxn).getBytes());
//        System.out.println("Sent to '/live/wallets/deposit' => " + new Gson().toJson(trxn));
//    }
//    private void doBrokerLinkage(StompSession session) {
//        HashMap<String, String> request = new HashMap<>();
//        request.put("subscriberId","5f71f129f2f8d1150e8f790d");
//        request.put("csdId", "0000000458160");
//        request.put("brokerId", "FBSL");
//        Gson gson = new Gson();
//        String json = gson.toJson(request);
//        ///wallets/deposit-result
//        session.subscribe("/session/brokers/linkage-result", this);
//        session.subscribe("/session/validation/errors", this);
//        session.subscribe("/session/exceptions/errors", this);
//
//        System.out.println("Subscribed to /brokers/linkage-result");
//        System.out.println("Subscribed to /session/validation/errors");
//        System.out.println("Subscribed to /session/exceptions/errors");
//
//        session.send("/live/brokers/linkage", json.getBytes());
//        System.out.println("Sent to '/live/brokers/linkage' => " + json);
//    }
//    private Subscriber getSubscriber() {
//        Subscriber subscriber = new Subscriber();
//        return subscriber;
//    }
    //Commodity
    /*private Order getOrder() {
        Order order =  Order.builder()
                .reference(BrivoHelper.generateRandomDigits(9).toString())
                .subscriberId("5f7495f348678628a6b4d2c0")
                .subscriberAtsId("TEJ281")
                .brokerAtsId("SBZL")
                .buyOrSell('1')
                .volume(1)
                .price(350.00)
                .comment("Testing 123")
                .securitySymbol("WHITE MAIZE")
                .securityAtsId("WMZE")
                .securityType("CASH")
                .orderType('2')
                .orderCapacity('A')
                .build();
        return order;
    }*/
    //Bond
    /*private Order getOrder() {
        Order order =  Order.builder()
                .reference(BrivoHelper.generateRandomDigits(9).toString())
                .subscriberId("5f7495f348678628a6b4d2c0")
                .subscriberAtsId("TEJ281")
                .brokerAtsId("SBZL")
                .buyOrSell('1')
                .volume(1)
                .price(140)
                .comment("Testing 123")
                .securitySymbol("31/10/24-0024-17.25")
                .securityAtsId("MWDS")
                .securityType("CORP")
                .orderType('2')
                .orderCapacity('A')
                .build();
        return order;
    }*/
    /*private Order getOrder() {
        Order order =  Order.builder()
                .reference(BrivoHelper.generateRandomDigits(9).toString())
                .subscriberId("5f7495f348678628a6b4d2c0")
                .subscriberAtsId("TEJ281")
                .brokerAtsId("SBZL")
                .buyOrSell('1')
                .volume(1)
                .price(100.00)
                .comment("Testing 123")
                .securitySymbol("15/02/28-0503-15")
                .securityAtsId("HFJJ")
                .securityType("CORP")
                .orderType('2')
                .orderCapacity('A')
                .build();
        return order;
    }*/
//    private Order getBuyOrder() {
//        Order order =  Order.builder()
//                .duplicateReference("c4f2eaf1-62de-4d52-a989-bae789b9ae76")//UUID.randomUUID().toString())
//                .subscriberId("5f71f129f2f8d1150e8f790d")
//                .subscriberAtsId("EIA018")
//                .brokerAtsId("SBZL")
//                .buyOrSell('1')
//                .volume(100)
//                .price(31.50)
//                .comment("Testing 123")
//                .securitySymbol("ATEL")
//                .securityAtsId("IQRN")
//                .securityType("CS")
//                .maturityDate("20201114")
//                .orderType('2')
//                .orderCapacity('A')
//                .build();
//        return order;
//    }
//    //Stock
//    private Order getSellOrder() {
//        Order order =  Order.builder()
//                .reference(BrivoHelper.generateRandomDigits(9).toString())
//                .subscriberId("5f71f129f2f8d1150e8f790d")
//                .subscriberAtsId("JZR126")
//                .brokerAtsId("SBZL")
//                .buyOrSell('2')
//                .volume(100)
//                .price(31.50)
//                .comment("Testing 123")
//                .securitySymbol("ATEL")
//                .securityAtsId("IQRN")
//                .securityType("CS")
//                .maturityDate("20201106")
//                .orderType('2')
//                .orderCapacity('A')
//                .build();
//        return order;
//    }
//
//    private  WalletTransaction getTrxn() {
//        WalletTransaction trxn =  WalletTransaction.builder()
//                .subscriberId("5f71fac6ab9487773d7f0792")//5f63bf43074ae82388baacba 5f63cc936e24387724ec2055
//                .brokerId("AASZ")//SBZL
//                .msisdn("260963621441")
//                .currency("ZMW")
//                .paymentMethod("MOMO")
//                .paymentChannel("MTN")
//                .amount(100.00)
//                .build();
//        return trxn;
//    }
//

}