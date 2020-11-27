 package LuseDma.ussd.testers;
 
 import java.util.HashMap;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.http.HTTPClientByOkHttp;

 public class HTTPTester
 {
   private static String SERVICE_URL = "http://127.0.0.1:8080";
   private static String SOURCE = "ZRA";
   private static String MSISDN = "260963621441";
   
   private static String AMOUNT = "200";
   public static void main(String[] args) {
     AppConfigHelper config = new AppConfigHelper();
     getUSSDTest();
   }
 
   public static void getUSSDTest() {
     try {
       //String destination = "http://127.0.0.1:8080/BumiLoansService/WIFIUSSDService";
       //String destination = "http://4a8a58d4.ngrok.io/BumiLoansService/WIFIUSSDService";
       String destination = "http://154.0.162.234:8080/BumiLoansService/WIFIUSSDService";
       
       HashMap<String, String> params = new HashMap<>();
       params.put("TransId", "100880183");
       params.put("RequestType", "1");
       params.put("MSISDN", "260965880096");
       params.put("SHORTCODE", "677");
       params.put("AppId", "677");
       params.put("USSDString", "*677#");
       
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       System.out.println("Received Response:" + response);
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
   /*public static void getClaimsTest() {
     try {
       HashMap<String, String> params = new HashMap<>();
 
 
       
       params.put("ssn", "100193871");
       params.put("nrc", "163759/16/1");
 
       
       JSONObject o = RemoteDataHelper.getNAPSAClaimsApi("claims", params);
       
       System.out.println("Received JSON Response:" + o.toString());
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
   public static void getMemberTest() {
     try {
       HashMap<String, String> params = new HashMap<>();
       params.put("ssn", "107194250");
       params.put("period", "2011/08");
       
       JSONObject o = RemoteDataHelper.getMemberApi("period_contribution", params);
       
       System.out.println("Received JSON Response:" + o.toString());
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
   
   public static void getEmployees() {
     try {
       JSONObject o = RemoteDataHelper.getEmployerApi("employees", AppConfigHelper.getEmployerAPIParams("969587"));
       System.out.println("Received JSON Response:" + o.toString());
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
   public static void getPendingNPINs() {
     try {
       HashMap<String, String> params = new HashMap<>();
       params.put("employer", "969587");
       params.put("payment_mode", "MOBILE");
       JSONObject o = RemoteDataHelper.getPendingNPINs(params);
       System.out.println("Received JSON Response:" + o.toString());
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
   public static void getSettleNPIN() {
     try {
       HashMap<String, String> params = new HashMap<>();
 
 
       
       params.put("npin", "96958717030177");
 
       
       params.put("mno", "MTN");
       params.put("mobile", "260966157164");
       
       params.put("delay", "0");
       JSONObject o = RemoteDataHelper.getSettleNPIN(params);
       System.out.println("Received JSON Response:" + o.toString());
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
 
   
   public static void getPendingReturns() {
     try {
       JSONObject o = RemoteDataHelper.getPendingReturns("969587");
       System.out.println("Received JSON Response:" + o.toString());
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
 */
 
   
   public static void getHTTPTest() {
     try {
       String destination = "http://196.46.196.39/rest_v1/api/mappingsapi/employer_employees?accountno=1833260";
       
       HashMap<String, String> params = new HashMap<>();
       params.put("accountno", "1833260");
 
       
       String response = HTTPClientByOkHttp.doGetRequest(destination, params);
       System.out.println("Received Response:" + response);
     } catch (Exception e) {
       e.printStackTrace();
     } 
   }
 }
