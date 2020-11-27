 package LuseDma.ussd.testers;
 
 import javax.xml.bind.JAXBContext;
 import javax.xml.bind.JAXBException;

 import LuseDma.ussd.helpers.USSDSessionHelper;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.handlers.USSDEngineCoreHandler;
 import LuseDma.ussd.models.MongoDBOld;
 import LuseDma.ussd.pojos.HauweiCPSMessage;

 public class HuaweiUSSDTester
 {
   private static String SessionID = "115";
   private static String AppID = "677";
   private static String Shortcode = "677";
   private static String SessionStatus = "CA";
   private static String MSISDN = "260966000001";
   private static String USSDString = "3";
   
   private static JAXBContext jaxbContext;
   
   private static HauweiCPSMessage hauweicpsmessage;
   private static String serviceresponse;
   
   public static void main(String[] args) {
     AppConfigHelper config = new AppConfigHelper();
     MongoDBOld mongodb = new MongoDBOld();
    
     try {
       jaxbContext = JAXBContext.newInstance(new Class[] { HauweiCPSMessage.class });
       String requestxml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?> <cps-message> <sequence_number>0</sequence_number> <version>16</version> <service_type>" + SessionStatus + "</service_type> " + "<source_addr>" + MSISDN + "</source_addr> " + "<dest_addr>" + Shortcode + "</dest_addr> " + "<timestamp>" + AppConfigHelper.getInstanceDate("yyyy/MM/dd HH:mm:ss") + "</timestamp> " + "<command_status>0</command_status> " + "<data_coding>4</data_coding> " + "<msg_len>" + USSDString.length() + "</msg_len> " + "<msg_content>" + USSDString + "</msg_content> \n" + "</cps-message> ";

       USSDSessionHelper ussdsessionhelper = getUSSDHelper(requestxml, mongodb);
       USSDEngineCoreHandler ussdsessionhelperhandler = new USSDEngineCoreHandler(ussdsessionhelper);
       USSDResponse ussdresponse = ussdsessionhelperhandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
       hauweicpsmessage = getUSSDResponse(ussdresponse);
       
       serviceresponse = AppConfigHelper.getXMLResponse(hauweicpsmessage, jaxbContext);
     }
     catch (JAXBException e) {
       AppConfigHelper.logger.error("Failed to generate XML response", e);
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       if (mongodb != null) mongodb.closeDb(); 
     } 
     System.out.println(serviceresponse);
   }
   
   public static USSDSessionHelper getUSSDHelper(String request, MongoDBOld mongodb) {
     USSDSessionHelper helper = null;
     
     try {
       jaxbContext = JAXBContext.newInstance(new Class[] { HauweiCPSMessage.class });
       HauweiCPSMessage message = (HauweiCPSMessage)AppConfigHelper.unmarshXML(request, jaxbContext);
       int sessionstatus = 0;
       if (message.getservice_type().equalsIgnoreCase("BR")) {
         sessionstatus = 1;
       } else if (message.getservice_type().equalsIgnoreCase("CA")) {
         sessionstatus = 2;
       } 
       helper = new USSDSessionHelper(message.getsource_addr(), mongodb);
       helper.setSessionID(message.getdest_addr());
       helper.setSequenceNumber(message.getsequence_number());
       helper.setSessionStatus(sessionstatus);
       helper.setAppID(message.getdest_addr());
       helper.setShortCode(message.getdest_addr());
       helper.setSessionStatus(sessionstatus);
       helper.setNetworkID(2);
       helper.setUserInput(parseUSSDBody(sessionstatus, message.getmsg_content()));
     } catch (JAXBException e) {
       AppConfigHelper.logger.error("Failed to generate XML response", e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("Failed to generate XML response", e);
     } 
     return helper;
   }
   public static String parseUSSDBody(int sessionstatus, String value) {
     if (sessionstatus == 1) {
       String v = value.substring(AppConfigHelper.USSD_MSG_INDEX);
       v = AppConfigHelper.parseUSSDHashChar(v);
       return v;
     } 
     return value;
   }
   
   public static HauweiCPSMessage getUSSDResponse(USSDResponse ussdresponse) {
     HauweiCPSMessage message = new HauweiCPSMessage();
     String sessionstatus = "";
     switch (ussdresponse.getSessionStatus()) {
       case 1:
         sessionstatus = "BN";
         break;
       case 2:
         sessionstatus = "CR";
         break;
       case 3:
         sessionstatus = "EA";
         break;
       case 4:
         sessionstatus = "AR";
         break;
     } 
     message.setsequence_number(ussdresponse.getUSSDSessionHelper().getSequenceNumber());
     message.setversion(16);
     message.setservice_type(sessionstatus);
     message.setsource_addr(ussdresponse.getUSSDSessionHelper().getShortCode());
     message.setdest_addr(ussdresponse.getUSSDSessionHelper().getShortCode());
     message.setdest_addr(ussdresponse.getUSSDSessionHelper().getMSISDN());
     message.settimestamp(AppConfigHelper.getInstanceDate("yyyy/MM/dd HH:mm:ss"));
     message.setcommand_status(0);
     message.setdata_coding(4);
     message.setmsg_len(ussdresponse.getMessageLength());
     message.setmsg_content(ussdresponse.getResponseMessage());
     return message;
   }
 }
