 package LuseDma.ussd.tangoiax;
 
 import java.util.Hashtable;

 import LuseDma.ussd.handlers.USSDEngineCoreHandler;
 import LuseDma.ussd.helpers.USSDSessionHelper;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.models.MongoDBOld;


 public class TangoIAXUSSDHandler
 {
   private Hashtable<String, String> serviceresponse;
   private String newLine = String.format("%n", new Object[0]);
   public Hashtable<String, String> USSD_MESSAGE(Hashtable<String, String> request) {
     AppConfigHelper config = new AppConfigHelper();
     MongoDBOld mongodb = new MongoDBOld();
     
     try { if (mongodb == null) {
         AppConfigHelper.logger.error("Database Connection are null");
         String body = "[" + AppConfigHelper.APP_NAME + "]" + this.newLine;
         body = body + "This service is temporarily unavailable." + this.newLine;
         body = body + "Please try again later." + this.newLine;
         this.serviceresponse = new Hashtable<>();
         this.serviceresponse.put("REQUEST_TYPE", "RESPONSE");
         this.serviceresponse.put("SESSION_ID", ((String)request.get("SESSION_ID")).toString());
         this.serviceresponse.put("SEQUENCE", ((String)request.get("SEQUENCE")).toString());
         this.serviceresponse.put("USSD_BODY", body);
         this.serviceresponse.put("END_OF_SESSION", "TRUE");
       } else {
         USSDSessionHelper mUSSDSessionHelper = getUSSDHelper(request, mongodb);
         USSDEngineCoreHandler mUSSDEngineCoreHandler = new USSDEngineCoreHandler(mUSSDSessionHelper);
         USSDResponse ussdresponse = mUSSDEngineCoreHandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
         this.serviceresponse = getUSSDResponse(ussdresponse);
       }  }
     catch (Throwable t)
     { AppConfigHelper.logger.error("Exception", t);
       String body = "[" + AppConfigHelper.APP_NAME + "]" + this.newLine;
       body = body + "This service is temporarily unavailable." + this.newLine;
       body = body + "Please try again later." + this.newLine;
       this.serviceresponse = new Hashtable<>();
       this.serviceresponse.put("REQUEST_TYPE", "RESPONSE");
       this.serviceresponse.put("SESSION_ID", ((String)request.get("SESSION_ID")).toString());
       this.serviceresponse.put("SEQUENCE", ((String)request.get("SEQUENCE")).toString());
       this.serviceresponse.put("USSD_BODY", body);
       this.serviceresponse.put("END_OF_SESSION", "TRUE"); }
     finally { 
       try { if (mongodb != null) mongodb.closeDb();  } catch (Throwable throwable) {} 
     }
     return this.serviceresponse;
   }
     
     
   private USSDSessionHelper getUSSDHelper(Hashtable<String, String> request, MongoDBOld mongodb ) {
     USSDSessionHelper helper = null;
     try {
       int sessionstatus = 0;
       if (request.containsKey("REQUEST_TYPE")) {
         String requesttype = ((String)request.get("REQUEST_TYPE")).toString();
         if (requesttype.equalsIgnoreCase("REQUEST")) {
           sessionstatus = 1;
         } else if (requesttype.equalsIgnoreCase("RESPONSE")) {
           sessionstatus = 2;
         }
       
       } else if (request.containsKey("SEQUENCE")) {
         if (Integer.valueOf(((String)request.get("SEQUENCE")).toString()).intValue() <= 1) {
           sessionstatus = 1;
         } else {
           sessionstatus = 2;
         } 
       } else {
         sessionstatus = 1;
       } 
       
       if (request.containsKey("END_OF_SESSION") && (
         (String)request.get("END_OF_SESSION")).toString().equalsIgnoreCase("TRUE")) {
         sessionstatus = 3;
       }
       
       helper = new USSDSessionHelper(((String)request.get("MOBILE_NUMBER")).toString(), mongodb);
       helper.setSessionID(((String)request.get("SESSION_ID")).toString());
       helper.setSequenceNumber(Integer.valueOf(((String)request.get("SEQUENCE")).toString()).intValue());
       helper.setSessionStatus(sessionstatus);
       helper.setAppID(request.get("SERVICE_KEY"));
       helper.setShortCode(request.get("SERVICE_KEY"));
       helper.setSessionStatus(sessionstatus);
       helper.setNetworkID(1);
       if (request.containsKey("USSD_BODY")) {
         helper.setUserInput(parseUSSDBody(sessionstatus, ((String)request.get("USSD_BODY")).toString()));
       } else {
         helper.setUserInput("");
       } 
     } catch (Exception e) {
       AppConfigHelper.logger.error("Exception", e);
       throw new RuntimeException("USSDSessionHelper Exception");
     } catch (Throwable e) {
       AppConfigHelper.logger.error("Exception", e);
       throw new RuntimeException("USSDSessionHelper Exception");
     } 
     return helper;
   }
   private String parseUSSDBody(int sessionstatus, String value) {
     if (sessionstatus == 1) {
       return value;
     }
     return value;
   }
   
   private Hashtable<String, String> getUSSDResponse(USSDResponse ussdresponse) {
     Hashtable<String, String> response = new Hashtable<>();
     String sessionstatus = "";
     String endofsession = "";
     switch (ussdresponse.getSessionStatus()) {
       case 1:
         sessionstatus = "REQUEST";
         break;
       case 2:
         sessionstatus = "REQUEST";
         break;
       case 3:
         sessionstatus = "RESPONSE";
         break;
       case 4:
         sessionstatus = "RESPONSE";
         break;
     } 
     switch (ussdresponse.getSessionStatus()) {
       case 1:
         endofsession = "FALSE";
         break;
       case 2:
         endofsession = "FALSE";
         break;
       case 3:
         endofsession = "TRUE";
         break;
       case 4:
         endofsession = "TRUE";
         break;
     } 
     
     response.put("RESPONSE_CODE", "0");
     response.put("REQUEST_TYPE", sessionstatus);
     response.put("SESSION_ID", ussdresponse.getUSSDSessionHelper().getSessionID());
     response.put("SEQUENCE", "" + ussdresponse.getUSSDSessionHelper().getSequenceNumber());
     response.put("USSD_BODY", ussdresponse.getResponseMessage());
     response.put("END_OF_SESSION", endofsession);
     return response;
   }
 }


