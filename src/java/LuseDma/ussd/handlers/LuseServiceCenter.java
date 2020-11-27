package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LuseServiceCenter {

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
            var client = HttpClient.newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
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
    public static JSONObject makeHttpRequest(String url, String payload) {
        JSONObject parsedResponse = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AppConfigHelper.LUSEDMA_BASE_URL + url))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AppConfigHelper.LUSEDMA_ACCESS_TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
            var client = HttpClient.newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONParser parse = new JSONParser();
            parsedResponse = (JSONObject) parse.parse(response.body());

        } catch (Exception e) {
            System.out.println("error ==> " + e.getMessage());
        }

        return parsedResponse;
    }

    //    get securities list
    public static JSONArray getSecurityList(String securityType) {
        JSONArray dataCollectionArray = new JSONArray();
        String url = "/securities/fetch";
        String payload = "{\"search\":\"\", \"securityType\":\"" + securityType + "\", \"sort\":\"1\", \"sortBy\":\"\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload);
        JSONArray dataArray = (JSONArray) parsedResObj.get("payload");

        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject tempObj = new JSONObject();
            JSONObject dataObj = (JSONObject) dataArray.get(i);
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
            tempObj.put("couponRate", dataObj.getOrDefault("couponRate", ""));
            tempObj.put("currency", dataObj.getOrDefault("currency", ""));
            dataCollectionArray.add(i, tempObj);
        }
        return dataCollectionArray;
    }

    // confirm buy
    public static JSONArray buyConfirmation() {
        JSONArray data = new JSONArray();
        try {
            JSONObject o = new JSONObject();
            o.put("id", "1");
            o.put("option", "Confirm");
            data.add(0, o);

            JSONObject o2 = new JSONObject();
            o2.put("id", "2");
            o2.put("option", "Cancel");
            data.add(1, o2);
        } catch (Exception e) {
        }
        return data;
    }

    //    get client information
    public static JSONArray clientInformation() {
        JSONArray dataCollectionArray = new JSONArray();
        String url = "/subscribers/find-one";
        String clientId = "5f74872edb5f79151b3170c0";
        String payload = "{\"id\":\"" + clientId + "\"}";

        JSONObject parsedResObj = makeHttpRequest(url, payload);
        JSONArray dataArray = (JSONArray) parsedResObj.get("payload");
        System.out.println("parsed data ==> "+parsedResObj);
        System.out.println("client data ==> "+dataArray);

//        for (int i = 0; i < dataArray.size(); i++) {
//            JSONObject tempObj = new JSONObject();
//            JSONObject dataObj = (JSONObject) dataArray.get(i);
//            JSONObject brokerList = (JSONObject) dataObj.get("atsAccounts");
//        }

        return dataArray;
    }
}
