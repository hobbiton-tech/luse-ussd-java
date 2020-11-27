 package LuseDma.ussd.comviva;
 
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import java.util.Map;
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 import LuseDma.ussd.helpers.USSDSessionHelper;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.common.BrivoHelper;
 import LuseDma.ussd.handlers.USSDEngineCoreHandler;
 import LuseDma.ussd.models.MongoDBOld;

 @WebServlet(name = "AirtelMoneyUSSDService", urlPatterns = {"/AirtelMoneyUSSDService"})
 public class AirtelMoMoUSSDService
   extends HttpServlet
 {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     AppConfigHelper config = new AppConfigHelper();
     MongoDBOld mongodb = new MongoDBOld();
     PrintWriter out = response.getWriter();
     
     Map headers = AppConfigHelper.getHeadersInfo(request);
     
     String SessionID = AppConfigHelper.getRequestValue(request, "SESSIONID", "1234");
     String IsNewRequest = AppConfigHelper.getRequestValue(request, "isnewrequest", "1");
     String MSISDN = AppConfigHelper.getRequestValue(request, "MSISDN", headers.getOrDefault("msisdn", "").toString());

     String Shortcode = "677";
     String USSDString = "";
     try {
       USSDString = URLDecoder.decode(AppConfigHelper.getRequestValue(request, "input", ""));
     } catch (Exception exception) {}

     AppConfigHelper.logger.error("USSD request:" + MSISDN + " option:" + USSDString);
     try {
       if (mongodb == null) {
         throw new RuntimeException("Database Connection are null");
       }
       
       int sessionstatus = getSessionStatusFromInput(USSDString);
       USSDSessionHelper mUSSDSessionHelper = new USSDSessionHelper(MSISDN, mongodb);
       
       mUSSDSessionHelper.setSessionID(SessionID);
       mUSSDSessionHelper.setAppID(SessionID);
       mUSSDSessionHelper.setShortCode(Shortcode);
       mUSSDSessionHelper.setSessionStatus(sessionstatus);
       mUSSDSessionHelper.setNetworkID(1);
       mUSSDSessionHelper.setUserInput(parseUSSDBody(sessionstatus, USSDString));
       USSDEngineCoreHandler mUSSDSessionHandler = new USSDEngineCoreHandler(mUSSDSessionHelper);
       USSDResponse mUSSDResponse = mUSSDSessionHandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
       
       response.setContentType("UTF-8");
       setUSSDResponseHeaders(mUSSDResponse, response);
       out.println(mUSSDResponse.getResponseMessage());
       
       AppConfigHelper.logger.error("USSD response:" + MSISDN + " message:" + mUSSDResponse.getResponseMessage() + "\n Bytes:" + (mUSSDResponse.getResponseMessage().getBytes("ASCII")).length + ", UTF Characters:" + mUSSDResponse.getResponseMessage().length() + ", GSM Characters:" + ((mUSSDResponse.getResponseMessage().getBytes()).length * 8 / 7));
     }
     catch (Throwable t) {
       if (t instanceof Exception) {
         AppConfigHelper.logger.error("Service Exception", t);
       } else if (t instanceof Error) {
         AppConfigHelper.logger.error("Service Error", t);
       } 
     } finally {
       out.close(); 
       try { if (mongodb != null) mongodb.closeDb();  } catch (Throwable throwable) {} 
     } 
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }

   public String getServletInfo() { return "Short description"; }

   public static String parseUSSDBody(int sessionstatus, String value) { return value; }
   
   public static int getSessionStatus(String request) {
     int value = 1;
     switch (request) { 
        case "1":
            value = 1;
            return value;
        case "0": 
            value = 2; 
            return value; 
     }  
     return value;
   }
   public static int getSessionStatusFromInput(String input) {
     int value = 1;
     if (input.isEmpty()) {
       value = 1;
     } else {
       value = 2;
     } 
     return value;
   }
   public static void setUSSDResponseHeaders(USSDResponse ussdresponse, HttpServletResponse response) {
     String sessionstatus = "FB";
     switch (ussdresponse.getSessionStatus()) { 
         case 1:
            sessionstatus = "FB"; 
            break;
         case 2:
            sessionstatus = "FC"; 
            break;
         case 3:
            sessionstatus = "FB";
            break; 
     }
     
     response.addHeader("Freeflow", sessionstatus);
     response.addHeader("charge", "N");
     response.addHeader("amount", "0");
     response.addHeader("cpRefId", BrivoHelper.generateAlphaNumericCode(8));
     response.addHeader("Expires", "-1");
     response.addHeader("Pragma", "no-cache");
     response.addHeader("Cache-Control", "max-age=0");
   }
 }
