 package LuseDma.ussd.testers;
 
 import LuseDma.ussd.helpers.USSDSessionHelper;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.handlers.USSDEngineCoreHandler;
 import LuseDma.ussd.models.MongoDBOld;


 public class ComvivaUSSDTester
 {
   private static String SessionID = "115";
   private static String AppID = "677";
   private static String Shortcode = "677";
   private static String SessionStatus = "1";
   private static String MSISDN = "260965880096";
   private static String USSDString = "677*1*1";

   
   public static void main(String[] args) {
     AppConfigHelper config = new AppConfigHelper();
     MongoDBOld mongodb = new MongoDBOld();
     try {
       USSDSessionHelper ussdsessionhelper = new USSDSessionHelper(MSISDN, mongodb);
       ussdsessionhelper.setSessionID(SessionID);
 
       
       ussdsessionhelper.setSessionStatus(getSessionStatus(SessionStatus));
       ussdsessionhelper.setNetworkID(3);
       ussdsessionhelper.setUserInput(parseUSSDBody(Integer.parseInt(SessionStatus), USSDString));
       USSDEngineCoreHandler ussdsessionhelperhandler = new USSDEngineCoreHandler(ussdsessionhelper);
       USSDResponse ussdresponse = ussdsessionhelperhandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
       System.out.println(getUSSDResponse(ussdresponse));
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       if (mongodb != null) mongodb.closeDb(); 
     } 
   }
   
   public static String parseUSSDBody(int sessionstatus, String value) {
     String USSD_MSG_BASE = "677*";
     int USSD_BASE_INDEX = 4;
     if (sessionstatus == 1) {
       int index = value.indexOf("*");
       if (index == -1)
       {
         return "";
       }
       if (value.indexOf(USSD_MSG_BASE) == -1) {
         return "";
       }
       
       return value.substring(USSD_BASE_INDEX);
     } 
 
     
     return value;
   }
   
   public static int getSessionStatus(String str) {
     int value = 1;
     switch (str) { case "FB":
        return value;
     case "FC": value = 2; 
        return value;
     case "":  
        return value;
     }  
     return value;
   }
   public static String getUSSDResponse(USSDResponse ussdresponse) {
     String sessionstatus = "1";
     switch (ussdresponse.getSessionStatus()) { case 1:
         sessionstatus = "FB"; 
         break;
       case 2:
         sessionstatus = "FC"; 
         break;
       case 3:
         sessionstatus = "FB";
         break; 
     }
 
     
     return "Freeflow: " + sessionstatus + " " + String.format("%n", new Object[0]) + "charge: N" + 
       String.format("%n", new Object[0]) + "amount: 0" + 
       String.format("%n", new Object[0]) + "cpRefId: 12345" + 
       String.format("%n", new Object[0]) + "Response Message.." + 
       String.format("%n", new Object[0]);
   }
 }


