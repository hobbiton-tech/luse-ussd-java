 package LuseDma.ussd.helpers;
 
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
import java.util.HashMap;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

 import LuseDma.ussd.models.USSDSessionModel;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.models.MongoDBOld;
 import LuseDma.ussd.pojos.MSISDNProvider;
 import LuseDma.ussd.pojos.MobileSession;

 public class USSDSessionHelper
 {
   private final MongoDBOld mongodb;
   private Boolean napsadb_connect;
   private String sessionid = "";
   private String appid = "";
   private String shortcode = "";
   private int sequencenumber = 0;
   private int servicestatus;
   private String MSISDN = "";
   private int networkid;
   private String ussdmessage = "";
 
   
   private String userinput = "";
   
   private int userinputhandle;
   private int sessiondirectionhandle;
   private final Long timestamp;
   private ArrayList<String> quickoptions;
   private final USSDSessionModel model;

   @Override
   public String toString() {
     return "USSDSessionHelper{" +
             "mongodb=" + mongodb +
             ", napsadb_connect=" + napsadb_connect +
             ", sessionid='" + sessionid + '\'' +
             ", appid='" + appid + '\'' +
             ", shortcode='" + shortcode + '\'' +
             ", sequencenumber=" + sequencenumber +
             ", servicestatus=" + servicestatus +
             ", MSISDN='" + MSISDN + '\'' +
             ", networkid=" + networkid +
             ", ussdmessage='" + ussdmessage + '\'' +
             ", userinput='" + userinput + '\'' +
             ", userinputhandle=" + userinputhandle +
             ", sessiondirectionhandle=" + sessiondirectionhandle +
             ", timestamp=" + timestamp +
             ", quickoptions=" + quickoptions +
             ", model=" + model +
             '}';
   }

   public USSDSessionHelper(String MSISDN, MongoDBOld mongodb) {
     this.MSISDN = MSISDN;
     this.mongodb = mongodb;
     this.model = new USSDSessionModel(this.mongodb);
     this.timestamp = getTimeStamp();
     this.napsadb_connect = Boolean.valueOf(false);
   }
   
   public String getSessionID() { return this.sessionid; }
   public int getSequenceNumber() { return this.sequencenumber; }
   public int getSessionStatus() { return this.servicestatus; }
   public String getMSISDN() { return this.MSISDN; }
   public int getNetworkID() { return this.networkid; }
   public String getUSSDMessage() { return this.ussdmessage; }
   public String getAppID() { return this.appid; }
   public String getShortCode() { return this.shortcode; }
   public String getUserInput() { return this.userinput; }
   public int getUserInputHandle() { return this.userinputhandle; }
   public int getSessionDirectionHandle() { return this.sessiondirectionhandle; }
   public ArrayList<String> getQuickOptions() { return this.quickoptions; }
   public Boolean getNAPSADbConnect() { return this.napsadb_connect; }
   public MongoDBOld getMongoDB() { return this.mongodb; }
   public USSDSessionModel getUSSDMongoModel() { return this.model; }
   
   public void setSessionID(String value) { this.sessionid = value; }
   public void setAppID(String value) { this.appid = value; }
   public void setShortCode(String value) { this.shortcode = value; }
   public void setSequenceNumber(int value) { this.sequencenumber = value; }
   public void setSessionStatus(int value) { this.servicestatus = value; }
   public void setMSISDN(String value) { this.MSISDN = value; }
   public void setNetworkID(int value) { this.networkid = value; }
   public void setUserInput(String value) { this.userinput = value; System.out.println("User Input:" + value); }
   
   private Boolean hasUserInput() {
     if (!this.userinput.equalsIgnoreCase("") || !this.userinput.equalsIgnoreCase(" ") || this.userinput != null) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   private Boolean hasQuickAccessUserInput() {
     if (hasUserInput().booleanValue()) {
       this.quickoptions = new ArrayList<>();
       Pattern p = Pattern.compile("([^(\\Q.*.\\E)]+)");
       Matcher matcher = p.matcher(this.userinput);
       while (matcher.find()) {
         this.quickoptions.add(matcher.group());
       }
       if (this.quickoptions.size() > 0) {
         return Boolean.valueOf(true);
       }
       return Boolean.valueOf(false);
     } 
     
     return Boolean.valueOf(false);
   }
 
 
   
   public void setUserInputHandle() {
     if (hasQuickAccessUserInput().booleanValue()) {
       
       this.userinputhandle = 2;
     } else {
       
       this.userinputhandle = 1;
     } 
   }
   
   public void setSessionDirectionHandle() {
     if (hasUserInput().booleanValue()) {
       AppConfigHelper.getInstance(); if (this.userinput.indexOf(AppConfigHelper.getReturnToListCharacter()) != -1) {
         this.sessiondirectionhandle = 4;
       } else {
         this.sessiondirectionhandle = 1;
       }
     }
     else {
       this.sessiondirectionhandle = 5;
     } 
   }
   
   public Boolean saveUSSDSession(int level, int mode) {
     if (isNotNew().booleanValue()) {
       return updateUSSDSession(level, mode);
     }
     return recordNewUSSDSession(level, mode);
   }
   
   public String getNetworkProviderName(int networkid) {
     switch (networkid) { case 1:
         return "Airtel";
       case 2: return "MTN";
       case 3: return "Zamtel"; }
      return "Unknown";
   }
 
   
   public static final Long getTimeStamp() { return Long.valueOf(System.currentTimeMillis() / 1000L); }
   
   private Boolean isNotNew() {
     try {
       return this.model.exists("{ msisdn : '" + this.MSISDN + "'}");
     } catch (Exception e) {
       AppConfigHelper.logger.error("Exception:", e);
       return Boolean.valueOf(false);
     } 
   }
   private Boolean recordNewUSSDSession(int level, int mode) {
     HashMap<String,Object> mobileProvider = AppConfigHelper.getMobileProvider(this.networkid);
     if ((Integer)mobileProvider.get("id") > 0) {
       MSISDNProvider msisdnprovider = new MSISDNProvider((Integer)mobileProvider.get("id"), (String)mobileProvider.get("name"));
       MobileSession session = new MobileSession(this.sessionid, this.MSISDN, msisdnprovider, level, mode, Boolean.valueOf(true));
       try {
         this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).save(session);
         return Boolean.valueOf(true);
       } catch (Exception e) {
         e.printStackTrace();
         AppConfigHelper.logger.error("Exception:", e);
         return Boolean.valueOf(false);
       } 
     } 
     return Boolean.valueOf(false);
   }
   
   private Boolean updateUSSDSession(int level, int mode) {
     HashMap<String,Object> mobileProvider = AppConfigHelper.getMobileProvider(this.networkid);
     if ((Integer)mobileProvider.get("id") > 0) {
       MSISDNProvider msisdnprovider = new MSISDNProvider((Integer)mobileProvider.get("id"), (String)mobileProvider.get("name"));
       try {
         this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.MSISDN + "'}").with("{$set: {lastseen: #}}", new Object[] { new Date() });
         this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.MSISDN + "'}").with("{$set: {sessionid: '" + this.sessionid + "',offset:0,priorperpage:0,perpage:0,currentpage:0,islastpage:0,security.authsessionlevel:-1,security.isloggedin:0,sessionlevel:'" + level + "',sessionmode:'" + mode + "'}}");
         return Boolean.valueOf(true);
       } catch (Exception e) {
         AppConfigHelper.logger.error("Exception:", e);
         return Boolean.valueOf(false);
       } 
     }
     return Boolean.valueOf(false);
   }
   
   public static String getISODate() {
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
 
     
     Calendar c1 = Calendar.getInstance();
     return sdf.format(c1.getTime());
   }
   public Boolean closeDbs() {
     try {
       this.mongodb.closeDb();
       return Boolean.valueOf(true);
     } catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
 }
