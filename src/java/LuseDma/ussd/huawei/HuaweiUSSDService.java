 package LuseDma.ussd.huawei;
 
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
 import LuseDma.ussd.common.BrivoHelper;
 import LuseDma.ussd.models.MongoDBOld;

 public class HuaweiUSSDService
   extends HttpServlet
 {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     AppConfigHelper config = new AppConfigHelper();
     MongoDBOld mongodb = new MongoDBOld();
     PrintWriter out = response.getWriter();
     
     String SessionID = request.getParameter("sessionid");
     String IsNewRequest = request.getParameter("isnewrequest");
     String MSISDN = request.getParameter("MSISDN");
     String Shortcode = "677";
     String USSDString = URLDecoder.decode(request.getParameter("input"));
     
     try {
       if (mongodb == null) {
         throw new RuntimeException("Database Connection are null");
       }
       int sessionstatus = getSessionStatus(IsNewRequest);
       USSDSessionHelper mUSSDSessionHelper = new USSDSessionHelper(MSISDN, mongodb);
       mUSSDSessionHelper.setSessionID(SessionID);
       mUSSDSessionHelper.setAppID(SessionID);
       mUSSDSessionHelper.setShortCode(Shortcode);
       mUSSDSessionHelper.setSessionStatus(sessionstatus);
       mUSSDSessionHelper.setNetworkID(2);
       mUSSDSessionHelper.setUserInput(parseUSSDBody(sessionstatus, USSDString));
       USSDEngineCoreHandler mUSSDEngineCoreHandler = new USSDEngineCoreHandler(mUSSDSessionHelper);
       USSDResponse ussdresponse = mUSSDEngineCoreHandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
       
       response.setContentType("UTF-8");
       setUSSDResponseHeaders(ussdresponse, response);
       out.println(ussdresponse.getResponseMessage());
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
   
   public static int getSessionStatus(String request) {
     int value = 1;
     switch (request) { case "1":
         value = 1;
         return value;case "0": value = 2; return value; }  value = 1; return value;
   }
   public static void setUSSDResponseHeaders(USSDResponse ussdresponse, HttpServletResponse response) {
     String sessionstatus = "FB";
     switch (ussdresponse.getSessionStatus()) { case 1:
         sessionstatus = "FB"; break;
       case 2:
         sessionstatus = "FC"; break;
       case 3:
         sessionstatus = "FB";
         break; }
     
     response.addHeader("Freeflow", sessionstatus);
     response.addHeader("charge", "N");
     response.addHeader("amount", "0");
     response.addHeader("cpRefId", BrivoHelper.generateAlphaNumericCode(8));
   }
 }

