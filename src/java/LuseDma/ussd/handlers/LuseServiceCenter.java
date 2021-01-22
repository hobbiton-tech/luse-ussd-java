package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Client;
import LuseDma.ussd.views.SecurityExploreView;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LuseServiceCenter {

    public static String socketRes;

    public static JSONArray getAllTestUsers() {
        JSONArray data = new JSONArray();
        org.json.simple.JSONObject JSONObject2 = new org.json.simple.JSONObject();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/user/all"))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("result ==>" + response.body());
            JSONParser parse = new JSONParser();
            JSONObject resObj = (JSONObject) parse.parse(response.body());
            JSONObject obj = (JSONObject) resObj.get("response");
            JSONArray obj2 = (JSONArray) obj.get("data");
            System.out.println("obj size ==> " + obj2.size());

            for (int i = 0; i < obj2.size(); i++) {
                JSONObject dataObj = (JSONObject) obj2.get(i);
                JSONObject o = new JSONObject();
                o.put("id", dataObj.getOrDefault("id", ""));
                o.put("email", dataObj.getOrDefault("email", ""));
                data.add(i, o);
            }
        } catch (Exception e) {
            System.out.println("error==>" + e.getMessage());
        }
        return data;
    }

    // make http request
    public static JSONObject makeHttpRequest(String endPont, String payload, String urlPath, String msisdn, MongoDBOld dbConn) {
        JSONObject parsedResponse = null;
        if (urlPath == "") {
            urlPath = AppConfigHelper.LUSEDMA_BASE_URL + endPont;
        }
//        System.out.println("url path ==> "+urlPath);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlPath))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AppConfigHelper.LUSEDMA_ACCESS_TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONParser parse = new JSONParser();
            parsedResponse = (JSONObject) parse.parse(response.body());

        } catch (Exception e) {
            System.out.println("error => " + e.getMessage());
            LuseServiceCenter.deleteUserStoredInfo(msisdn, dbConn);
            System.exit(1);
            System.out.println("error ==> " + e.getMessage());
        }

        return parsedResponse;
    }

    //    get securities list
    public static JSONArray getSecurityList(String securityType, String securityFilter, String msisdn, MongoDBOld dbConn) {
        JSONArray dataCollectionArray = new JSONArray();
        String url = "/securities/fetch";
        String payload = "{\"search\":\"\", \"securityType\":\"" + securityType + "\", \"sort\":\"1\", \"sortBy\":\"\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, dbConn);
        try {
            JSONArray dataArray = (JSONArray) parsedResObj.get("payload");
            JSONArray dataArrayFilter = new JSONArray();
            JSONArray finalDataArray = new JSONArray();

            System.out.println("filter value ==> " + securityFilter);

            if (securityFilter != "") {
                System.out.println("security filter not null");
                for (int i = 0; i < dataArray.size(); i++) {
                    JSONObject dataObj = (JSONObject) dataArray.get(i);

                    String securityTypeFilter = dataObj.getOrDefault("securityType", "").toString();
                    String securityCodeFilter = dataObj.getOrDefault("securityCode", "").toString();
                    String secuirtyCsdId = dataObj.getOrDefault("csdId", "").toString();

                    switch (securityTypeFilter) {
                        case "CORP":
                            if (secuirtyCsdId.startsWith(securityFilter)) {
                                dataArrayFilter.add(dataObj);
                            }
                            break;
                        default:
                            if (securityCodeFilter.startsWith(securityFilter)) {
                                dataArrayFilter.add(dataObj);
                            }
                            break;
                    }
                }
                finalDataArray = dataArrayFilter;
            } else {
                finalDataArray = dataArray;
            }


            System.out.println("filtered array ==> " + dataArrayFilter);
            System.out.println("final data array ==> " + finalDataArray);

            for (int i = 0; i < finalDataArray.size(); i++) {
                JSONObject tempObj = new JSONObject();
                JSONObject dataObj = (JSONObject) finalDataArray.get(i);
                JSONObject marketData = (JSONObject) dataObj.getOrDefault("marketData", "");

                tempObj.put("id", dataObj.getOrDefault("id", ""));
                tempObj.put("securityType", dataObj.getOrDefault("securityType", ""));
                tempObj.put("csdId", dataObj.getOrDefault("csdId", ""));
                tempObj.put("atsId", dataObj.getOrDefault("atsId", ""));
                tempObj.put("securityCode", dataObj.getOrDefault("securityCode", ""));
                tempObj.put("marketCap", String.valueOf(marketData.getOrDefault("marketCap", "")));
                tempObj.put("changeInPrice", String.valueOf(marketData.getOrDefault("changeInPrice", "")));
                tempObj.put("closingPrice", String.valueOf(marketData.getOrDefault("closingPrice", "")));
                tempObj.put("settlementPrice", String.valueOf(marketData.getOrDefault("settlementPrice", "")));
                tempObj.put("openInterest", String.valueOf(marketData.getOrDefault("openInterest", "")));
                tempObj.put("symbol", dataObj.getOrDefault("symbol", ""));
                tempObj.put("issueDate", dataObj.getOrDefault("issueDate", ""));
                tempObj.put("maturityDate", dataObj.getOrDefault("maturityDate", ""));
                tempObj.put("description", dataObj.getOrDefault("description", ""));
                tempObj.put("name", dataObj.getOrDefault("name", ""));
                tempObj.put("couponRate", String.valueOf(dataObj.getOrDefault("couponRate", "")));
                tempObj.put("currency", dataObj.getOrDefault("currency", ""));
                dataCollectionArray.add(tempObj);
            }
        } catch (Exception e) {
            System.out.println("error => " + e.getMessage());
            LuseServiceCenter.deleteUserStoredInfo(msisdn, dbConn);
        }
        return dataCollectionArray;
    }

    //  procced option
    public static JSONArray proceedOption() {
        JSONArray data = new JSONArray();
        try {
            JSONObject o = new JSONObject();
            o.put("id", "1");
            o.put("option", "proceed");
            data.add(o);

        } catch (Exception e) {
        }
        return data;
    }

    //confirm option
    public static JSONArray confirmOption() {
        JSONArray data = new JSONArray();
        try {
            JSONObject o = new JSONObject();
            o.put("id", "1");
            o.put("option", "confirm");
            data.add(o);

        } catch (Exception e) {
        }
        return data;
    }

    //confirm option
    public static JSONArray optionSelectors(String action, String buyOrSell) {
        JSONArray data = new JSONArray();
        JSONObject o = new JSONObject();
        JSONObject o2 = new JSONObject();
        String securityAction = "Buy";
        if (buyOrSell.equals("2")) {
            securityAction = "Sell";
        }
        switch (action) {
            case "securityDetails":
                o.put("id", "1");
                o.put("option", "View Details");
                data.add(0, o);


                o2.put("id", "2");
                o2.put("option", securityAction);
                data.add(1, o2);
                break;
            case "orderDetails":
                o.put("id", "1");
                o.put("option", "Order Details");
                data.add(0, o);

                o2.put("id", "2");
                o2.put("option", "Confirm " + securityAction);
                data.add(1, o2);
                break;
        }

        return data;
    }

    //    get client information
    public static JSONArray clientInformation(String action, String msisdn, MongoDBOld dbConn) {
        System.out.println("clientInformation function");
        JSONArray dataCollectionArray = new JSONArray();

        String url = "/subscribers/find-one";
        String payload = "{\"id\":\"" + AppConfigHelper.LUSE_CLIENT_ID + "\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, dbConn);
        try {
            JSONObject clientObj = (JSONObject) parsedResObj;
            JSONObject paylaod = (JSONObject) clientObj.get("payload");

            switch (action) {
                case "brokers":
                    JSONArray atsAccounts = (JSONArray) paylaod.get("atsAccounts");
                    for (int i = 0; i < atsAccounts.size(); i++) {
                        JSONObject brokersObj = (JSONObject) atsAccounts.get(i);
                        JSONObject broker = new JSONObject();
                        broker.put("brokerId", brokersObj.get("brokerId"));
                        broker.put("atsId", brokersObj.get("atsId"));
                        dataCollectionArray.add(i, broker);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("error => " + e.getMessage());
            LuseServiceCenter.deleteUserStoredInfo(msisdn, dbConn);
        }

        return dataCollectionArray;
    }

    // save client
    public static void saveClient(MongoDBOld DbConnection, String msisdn, String clientId, MongoDBOld dbConn) {
        System.out.println("saving user");
        String url = "/subscribers/find-one";
        String payload = "{\"id\":\"" + clientId + "\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, dbConn);
        JSONObject clientObj = (JSONObject) parsedResObj;
        JSONObject payloadObj = (JSONObject) clientObj.get("payload");
        JSONArray atsAccountsArray = (JSONArray) payloadObj.get("atsAccounts");
        JSONObject atsAccountsObjIndex0 = (JSONObject) atsAccountsArray.get(0);


        Client client = new Client();
        ClientModel clientModel = new ClientModel(DbConnection);

        client.setId((String) payloadObj.getOrDefault("id", ""));
        client.setCsdId((String) payloadObj.getOrDefault("csdId", ""));
        client.setWalletBalance(String.valueOf(parsedResObj.getOrDefault("walletBalance", "").toString()));
        client.setAccessToken((String) payloadObj.getOrDefault("accessToken", ""));
        client.setFullname((String) payloadObj.getOrDefault("fullname", ""));
        client.setAtsAccountsAtsId(atsAccountsObjIndex0.getOrDefault("atsId", "").toString());
        client.setAtsAccountsBrokerId(atsAccountsObjIndex0.getOrDefault("brokerId", "").toString());
        client.setMsisdn(msisdn);

        Boolean result = clientModel.save(client);
        System.out.println("user save status ==> " + result);
    }

    // buy security
    public static void orderSecurity(JSONObject payload, MongoDBOld mongodb, String msisdn) {
        System.out.println("payload recieved==> " + payload);
        SockJsSpringClient client = new SockJsSpringClient();
        client.connectWebsocket("orderSecurity", payload, mongodb, msisdn);
    }

    //    get client information
    public static void sendSMS(String msisdn, String message, MongoDBOld dbConn) {
        System.out.println("sending sms");

        String url = AppConfigHelper.SMS_GATEWAY_URL;
        String payload = "{\"MSISDN\":\"" + msisdn + "\", \"message\":\"" + message + "\"}";
        System.out.println("payload sent ==> " + payload);

        makeHttpRequest("", payload, url, msisdn, dbConn);
        System.out.println("SMS sent");
    }

//    // bonds calculator
//    public static JSONObject bondsCalculator(JSONObject bondPricePayload) {
//        String url = "/securities/bond-calculator";
//        String payload = "{\"csdId\":\"" + bondPricePayload.get("csdId").toString() + "\", \"yield\":" + 0.1 + ",\"price\":" + Double.parseDouble(bondPricePayload.get("price").toString()) + "}";
//        System.out.println("bond payload sent ==> " + payload);
//
//        JSONObject parsedObj = makeHttpRequest(url, payload, "");
//        JSONObject requestRes = (JSONObject) parsedObj.get("payload");
//        JSONObject payloadRes = (JSONObject) requestRes;
//
//        System.out.println("response ==> " + parsedObj);
//
//        JSONObject result = new JSONObject();
//        result.put("cleanPrice", payloadRes.getOrDefault("cleanPrice", "").toString());
//        result.put("currentPrice", payloadRes.getOrDefault("currentPrice", "").toString());
//        result.put("currentYieldToMaturity", payloadRes.getOrDefault("currentYieldToMaturity", "").toString());
//
//        return result;
//    }

    // fund wallet
    public static void fundWallet(JSONObject payload, MongoDBOld mongodb, String msisdn) {
        System.out.println("payload recieved==> " + payload);
        SockJsSpringClient client = new SockJsSpringClient();
        client.connectWebsocket("fundAccount", payload, mongodb, msisdn);
    }

    //get portfolio
    public static JSONArray getClientPorfolio(String subscriberId, String csdId, String msisdn, MongoDBOld dbConn) {
        JSONArray dataCollectionArray = new JSONArray();
        String url = "/subscribers/holdings";
        String payload = "{\"subscriberId\":\"" + subscriberId + "\", \"csdId\":\"" + csdId + "\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, dbConn);

        try {
            JSONArray dataArray = (JSONArray) parsedResObj.get("payload");


            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject tempObj = new JSONObject();
                JSONObject dataObj = (JSONObject) dataArray.get(i);

                tempObj.put("csdId", dataObj.getOrDefault("csdId", ""));
                tempObj.put("securityCode", dataObj.getOrDefault("securityCode", ""));
                tempObj.put("securityName", dataObj.getOrDefault("securityName", ""));
                tempObj.put("shortLongIndicator", dataObj.getOrDefault("shortLongIndicator", ""));
                tempObj.put("holdingsBalance", dataObj.getOrDefault("holdingsBalance", ""));
                tempObj.put("availableBalance", dataObj.getOrDefault("availableBalance", ""));
                dataCollectionArray.add(tempObj);
            }
        } catch (Exception e) {
            System.out.println("error => " + e.getMessage());
            LuseServiceCenter.deleteUserStoredInfo(msisdn, dbConn);
//            System.exit(0);
        }

        return dataCollectionArray;
    }

    //    login user
    public static boolean loginClient(MongoDBOld DbConnection, String msisdn, String password) {
        Boolean result = false;
        System.out.println("login user attempt. Username=>" + msisdn + " password=>" + password);
        String url = "/subscribers/account/login";
        String payload = "{\"username\":\"" + msisdn + "\", \"password\": \"" + password + "\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, DbConnection);

        try {
            JSONObject clientObj = (JSONObject) parsedResObj;
            JSONObject payloadObj = (JSONObject) clientObj.get("payload");
            JSONArray atsAccountsArray = (JSONArray) payloadObj.get("atsAccounts");
            JSONObject atsAccountsObjIndex0 = (JSONObject) atsAccountsArray.get(0);


            Client client = new Client();
            ClientModel clientModel = new ClientModel(DbConnection);

            AppConfigHelper.setClientId((String) payloadObj.getOrDefault("id", ""));
            AppConfigHelper.setAccessToken((String) payloadObj.getOrDefault("accessToken", ""));
            AppConfigHelper.setClientCsdId(client.getCsdId());

            client.setId((String) payloadObj.getOrDefault("id", ""));
            client.setAccessToken((String) payloadObj.getOrDefault("accessToken", ""));
            client.setCsdId((String) payloadObj.getOrDefault("csdId", ""));
            client.setWalletBalance(String.valueOf(payloadObj.getOrDefault("walletBalance", "").toString()));
            client.setAccessToken((String) payloadObj.getOrDefault("accessToken", ""));
            client.setFullname((String) payloadObj.getOrDefault("fullname", ""));
            client.setAtsAccountsAtsId(atsAccountsObjIndex0.getOrDefault("atsId", "").toString());
            client.setAtsAccountsBrokerId(atsAccountsObjIndex0.getOrDefault("brokerId", "").toString());
            client.setMsisdn(msisdn);

            result = clientModel.save(client);
            System.out.println("user save status ==> " + result);
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public static boolean changePassword(String oldPassword, String newPassword, String msisdn, MongoDBOld dbConn) {
        Boolean result = false;
        System.out.println("change password attempt");
        String url = "/subscribers/change-password";
        String payload = "{\"newPassword\":\"" + newPassword + "\", \"oldPassword\": \"" + oldPassword + "\"}";
        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, dbConn);
        try {
            if (String.valueOf(parsedResObj.getOrDefault("code", "")).equals("0")) {
                result = true;
                LuseServiceCenter.sendSMS(msisdn, "Your account password has been successfully changed.", dbConn);
            }
        } catch (Exception e) {
            System.out.println("error => " + e.getMessage());
            LuseServiceCenter.deleteUserStoredInfo(msisdn, dbConn);
        }
        return result;
    }

    public static void deleteUserStoredInfo(String msisdn, MongoDBOld dbConn) {
        System.out.println("deleting user ");
        ClientModel clientModel = new ClientModel(dbConn);
        clientModel.delete(msisdn);
    }

    //    get all brokers
    public static JSONArray getAllBrokers(String msisdn, MongoDBOld dbConn) {
        System.out.println("all brokers function");
        JSONArray dataCollectionArray = new JSONArray();

        String url = "/brokers/fetch";
        String payload = "{\"search\": \"\", \"sort\": \"1\", \"sortBy\": \"name\", \"status\": \"1\" }";

        JSONObject parsedResObj = makeHttpRequest(url, payload, "", msisdn, dbConn);
        try {
            JSONObject clientObj = (JSONObject) parsedResObj;
            JSONArray allBrokers = (JSONArray) clientObj.get("payload");

            for (int i = 0; i < allBrokers.size(); i++) {
                JSONObject brokersObj = (JSONObject) allBrokers.get(i);
                JSONObject broker = new JSONObject();
                broker.put("name", brokersObj.get("name"));
                broker.put("atsId", brokersObj.get("atsId"));
                broker.put("csdId", brokersObj.get("csdId"));
                dataCollectionArray.add(i, broker);
            }

        } catch (Exception e) {
            System.out.println("error => " + e.getMessage());
            LuseServiceCenter.deleteUserStoredInfo(msisdn, dbConn);
        }
        System.out.println(dataCollectionArray);
        return dataCollectionArray;

    }

    // add broker
    public static void addBroker(JSONObject payload, MongoDBOld mongodb, String msisdn) {
        System.out.println("payload recieved==> " + payload);
        SockJsSpringClient client = new SockJsSpringClient();
        client.connectWebsocket("addBroker", payload, mongodb, msisdn);
    }

}
