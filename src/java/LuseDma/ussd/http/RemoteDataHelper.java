 package LuseDma.ussd.http;
 
 import LuseDma.ussd.common.AppConfigHelper;

 import java.util.HashMap;
 import java.util.Map;
 import org.json.JSONObject;
 
 
 public class RemoteDataHelper
 {
   public static JSONObject runLoanOfferPayment(Map params) {
     JSONObject resp = new JSONObject();
     try {
       //String destination = AppConfigHelper.eNAPSA_BASE_URL + "/initmobilepayment";
       //AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       //String response = HTTPClientByOkHttp.doLazyGetRequest(destination, params);
       
       String response = "{\"statusCode\":\"SUCCESS\",\"statusDescription\":\"Payment successfully initiated, please check your Phone to approve the Payment.\"}";
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:runLoanOfferPayment::", e);
     } 
     return resp;
   }
     public static JSONObject runRepayment(HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       //String destination = "";//AppConfigHelper.eNAPSA_BASE_URL + "/ecispayment";
       // AppConfigHelper.logger.error("Sent Request URL:" + destination);
       //AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       //String response = HTTPClientByOkHttp.doLazyGetRequest(destination, params);
       //String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       String response = "{\"statusCode\":\"SUCCESS\",\"statusDescription\":\"SUCCESS\"}";
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getSettleNPIN::", e);
     } 
     return resp;
   }
  /* public static JSONObject getMappings(String endpoint, HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.NPM_BASE_URL + "/mappingsapi/" + endpoint;
       
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
     }
     catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getEmployees:", e);
     } 
     return resp;
   }
   public static JSONObject getMemberApi(String endpoint, HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.NPM_BASE_URL + "/memberapi/" + endpoint;
       AppConfigHelper.logger.error("Sent Request URL:" + destination);
       AppConfigHelper.logger.error("Sent Request:" + new Gson().toJson(params));
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getMembersApi:", e);
     } 
     return resp;
   }
 
   
   public static JSONObject getEmployerApi(String endpoint, HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.NPM_BASE_URL + "/employerapi/" + endpoint;
       AppConfigHelper.logger.error("Sent Request URL:" + destination);
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
       AppConfigHelper.logger.error("Received Response:" + response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getEmployerApi:", e);
     } 
     return resp;
   }
   public static JSONObject getNAPSAClaimsApi(String endpoint, HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.NPM_BASE_URL + "/napsabenefitsapi/" + endpoint;
       AppConfigHelper.logger.error("Sent Request URL:" + destination);
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
       AppConfigHelper.logger.error("Received Response:" + response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getEmployerApi:", e);
     } 
     return resp;
   }
   public static JSONObject getZNPFClaimsApi(String endpoint, HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.NPM_BASE_URL + "/napsabenefitsapi/" + endpoint;
       AppConfigHelper.logger.error("Sent Request URL:" + destination);
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
       AppConfigHelper.logger.error("Received Response:" + response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getEmployerApi:", e);
     } 
     return resp;
   }
   public static JSONObject getENAPSAApi(String endpoint, HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.eNAPSA_BASE_URL + "/"+ endpoint;
       AppConfigHelper.logger.error("Sent Request URL:" + destination);
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
       AppConfigHelper.logger.error("Received Response:" + response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getPaymentModes:", e);
     } 
     return resp;
   }
   
   
   public static JSONObject getPendingNPINs(HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.eNAPSA_BASE_URL + "/getpendingnpins";
       AppConfigHelper.logger.error("Request URL:" + destination);
       AppConfigHelper.logger.error("Request Body:" + GsonUtil.getInstance().toJson(params));
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
       AppConfigHelper.logger.error("Received Response:" + response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getPendingNPINs:", e);
     } 
     return resp;
   }
   public static JSONObject getPendingReturns(String accountno) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.eNAPSA_BASE_URL + "/getpendingreturns";
       
       HashMap<String, String> params = new HashMap<>();
       params.put("employer", accountno);
 
 
       
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       resp = new JSONObject(response);
     }
     catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getPendingReturns:", e);
     } 
     return resp;
   }
   public static JSONObject getPaymentModes() {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.eNAPSA_BASE_URL + "/getpaymentmodes";
       HashMap<String, String> params = new HashMap<>();
       
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getPaymentModes:", e);
     } 
     return resp;
   }
   public static JSONObject getNPINLookUp(String npin) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.BRIDGE_BASE_URL + "/get_npin_details";
       GetNPINDetailsRequest v = new GetNPINDetailsRequest();
       v.setProviderID(AppConfigHelper.ENAPSA_API_PROVIDER_ID);
       v.setNPIN(npin);
       
       String response = HTTPClientCoreJava.rawpost(destination, (new Gson()).toJson(v));
       
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getNPINLookUp:", e);
     } 
     return resp;
   }
   public static JSONObject getUploadReturn(ReturnUploadRequest request) {
     JSONObject resp = new JSONObject();
     String destination = AppConfigHelper.BRIDGE_BASE_URL + "/return_upload";
     try {
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(request));
       String response = HTTPClientCoreJava.rawpost(destination, (new Gson()).toJson(request));
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getUploadReturn:", e);
     } 
     return resp;
   }
   public static JSONObject getGenerateNPIN(GenerateNPINRequest request) {
     JSONObject resp = new JSONObject();
     String destination = AppConfigHelper.BRIDGE_BASE_URL + "/generate_npin";
     try {
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(request));
       String response = HTTPClientCoreJava.rawpost(destination, (new Gson()).toJson(request));
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getGenerateNPIN:", e);
     } 
     return resp;
   }
   public static JSONObject getSettleNPIN(HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.eNAPSA_BASE_URL + "/initmobilepayment";
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       String response = HTTPClientByOkHttp.doLazyGetRequest(destination, params);
       
       response = "{\"statusCode\":\"SUCCESS\",\"statusDescription\":\"Payment successfully initiated, please check your Phone to approve the Payment.\"}";
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getSettleNPIN::", e);
     } 
     return resp;
   }
   
   public static JSONObject getPostECISPayment(HashMap params) {
     JSONObject resp = new JSONObject();
     try {
       String destination = AppConfigHelper.eNAPSA_BASE_URL + "/ecispayment";
        AppConfigHelper.logger.error("Sent Request URL:" + destination);
       AppConfigHelper.logger.error("Sent Request:" + GsonUtil.getInstance().toJson(params));
       //String response = HTTPClientByOkHttp.doLazyGetRequest(destination, params);
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       //response = "{\"statusCode\":\"SUCCESS\",\"statusDescription\":\"SUCCESS\"}";
       AppConfigHelper.logger.error("Received Response:" + response);
       resp = new JSONObject(response);
     } catch (Exception e) {
       AppConfigHelper.logger.error("RemoteDataHelper:getSettleNPIN::", e);
     } 
     return resp;
   }*/
 }

