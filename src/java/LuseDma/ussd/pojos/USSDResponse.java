 package LuseDma.ussd.pojos;
 
 import LuseDma.ussd.helpers.USSDSessionHelper;
 import org.apache.commons.lang.StringEscapeUtils;
 

 public class USSDResponse
 {
   private USSDSessionHelper ussdsessionhelper = null;
   private String responsemessage = null;
   private int sessionstatus = 1; public USSDResponse() {}
   private int messagelength = 0;
   
   public USSDResponse(USSDSessionHelper object) {
     this();
     this.ussdsessionhelper = object;
   }
   public USSDResponse(USSDSessionHelper object, String responsemessage, int sessionstatus) {
     this();
     this.ussdsessionhelper = object;
     this.responsemessage = responsemessage;
     this.sessionstatus = sessionstatus;
     this.messagelength = this.responsemessage.length();
   }
   
   public void setSessionStatus(int v) { this.sessionstatus = v; }
   
   public void setResponseMessage(String v) {
     this.responsemessage = StringEscapeUtils.unescapeHtml(v);
     this.messagelength = this.responsemessage.length();
   }
   
   public void setUSSDSessionHelper(USSDSessionHelper v) { this.ussdsessionhelper = v; }
 
   
   public int getSessionStatus() { return this.sessionstatus; }
 
   
   public String getResponseMessage() { return this.responsemessage; }
 
 
   
   public int getMessageLength() { return this.messagelength; }
 
   
   public USSDSessionHelper getUSSDSessionHelper() { return this.ussdsessionhelper; }
 }

