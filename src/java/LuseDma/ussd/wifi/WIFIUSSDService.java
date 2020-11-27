 package LuseDma.ussd.wifi;
 
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 import LuseDma.ussd.handlers.USSDEngineCoreHandler;
 import LuseDma.ussd.helpers.USSDSessionHelper;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.models.MongoDBOld;

 public class WIFIUSSDService   extends HttpServlet {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     AppConfigHelper config = new AppConfigHelper();
     MongoDBOld mongodb = new MongoDBOld();
     PrintWriter out = response.getWriter();
     
     String AppID = AppConfigHelper.getRequestValue(request, "AppId", "677");
     String SessionID = AppConfigHelper.getRequestValue(request, "TransId", "1234");
     String SessionStatus = AppConfigHelper.getRequestValue(request, "RequestType", "1");
     String MSISDN = AppConfigHelper.getRequestValue(request, "MSISDN", "");
     String Shortcode = "677";
     String USSDString = "";
     try {
       USSDString = URLDecoder.decode(AppConfigHelper.getRequestValue(request, "USSDString", ""));
     } catch (Exception exception) {}
 
 
     
     try {
       if (mongodb == null ) {
         throw new RuntimeException("Database Connection are null");
       }
       USSDSessionHelper mUSSDSessionHelper = new USSDSessionHelper(MSISDN, mongodb);
       mUSSDSessionHelper.setSessionID(SessionID);
       mUSSDSessionHelper.setAppID(AppID);
       mUSSDSessionHelper.setShortCode(Shortcode);
       mUSSDSessionHelper.setSessionStatus(Integer.parseInt(SessionStatus));
       mUSSDSessionHelper.setNetworkID(3);
       mUSSDSessionHelper.setUserInput(parseUSSDBody(Integer.parseInt(SessionStatus), USSDString));
       USSDEngineCoreHandler mUSSDEngineCoreHandler = new USSDEngineCoreHandler(mUSSDSessionHelper);
       USSDResponse ussdresponse = mUSSDEngineCoreHandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
       String serviceresponse = getUSSDResponse(ussdresponse);
       response.setContentType("text/plain");
       out.println(serviceresponse);
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
 
 
   
   public static String parseUSSDBody(int sessionstatus, String value) {
     if (sessionstatus == 1) {
       String v = value.substring(AppConfigHelper.USSD_MSG_INDEX);
       v = AppConfigHelper.parseUSSDHashChar(v);
       return v;
     } 
     return value.substring(AppConfigHelper.USSD_MSG_INDEX);
   }
   
   public static String getUSSDResponse(USSDResponse ussdresponse) {
     String sessionstatus = "1";
     switch (ussdresponse.getSessionStatus()) { case 1:
         sessionstatus = "1"; break;
       case 2:
         sessionstatus = "2"; break;
       case 3:
         sessionstatus = "1";
         break; }
     
     return "&TransId=" + ussdresponse.getUSSDSessionHelper().getSessionID() + "&RequestType=" + sessionstatus + "&MSISDN=" + ussdresponse.getUSSDSessionHelper().getMSISDN() + "&AppId=" + ussdresponse.getUSSDSessionHelper().getAppID() + "&USSDString=" + ussdresponse.getResponseMessage();
   }
 }

