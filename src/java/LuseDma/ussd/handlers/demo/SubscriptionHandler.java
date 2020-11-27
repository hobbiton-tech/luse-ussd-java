 package LuseDma.ussd.handlers.demo;
 
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.handlers.MainMenuHandler;
 import LuseDma.ussd.helpers.USSDSession;

 import java.io.FileNotFoundException;


 public class SubscriptionHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private MainMenuHandler MainMenuHandler = null;
   private USSDResponse response = null;
   private String responsemessage = "";
   private String newLine = String.format("%n", new Object[0]);
   
   public SubscriptionHandler(USSDSession ussdsession) { this.ussdsession = ussdsession; }
   
   public USSDResponse runSession() throws FileNotFoundException {
     switch (this.ussdsession.getSessionLevel()) {
       case 0:
         this.ussdsession.saveUSSDSession(1);
         
         this.ussdsession.saveSessionMode(0);
         return this.ussdsession.buildUSSDResponse(getMenu(), 2);
       
       case 1:
         this.ussdsession.saveUSSDSession(2);
         
         return runHigherSessionLevelHandler();
     } 
     this.ussdsession.saveUSSDSession(-1);
     this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
     this.response = this.MainMenuHandler.runSession();
     return this.response;
   }
   
   private String getMenu() {
     if (this.ussdsession.getMobileSession().getSessionStatus() == 1) {
       this.responsemessage = "Notifications:" + this.newLine;
       this.responsemessage += "Are you sure you want to STOP receiving notifications from " + AppConfigHelper.APP_NAME + this.newLine;
       this.responsemessage += AppConfigHelper.confirmActionText + this.newLine;
     } else {
       this.ussdsession.saveNotificationsSubscribe();
       this.ussdsession.saveUSSDSession(1);
       this.responsemessage = "Notifications:" + this.newLine;
       this.responsemessage += "You have successfully subscribed for notifications from " + AppConfigHelper.APP_NAME + this.newLine;
     } 
     
     AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
     return this.responsemessage;
   }
   public USSDResponse runHigherSessionLevelHandler() {
     if (this.ussdsession.getUserInput().equalsIgnoreCase(AppConfigHelper.CONFIRM_ACTION_CHARACTER)) {
       this.ussdsession.saveNotificationsUnsubscribe();
       this.ussdsession.saveUSSDSession(1);
       this.ussdsession.saveSessionMode(0);
       this.responsemessage = "Notifications:" + this.newLine;
       this.responsemessage += "You have successfully un-subscribed for notifications from " + AppConfigHelper.APP_NAME + this.newLine;
       AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
       return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } 
     this.ussdsession.saveUSSDSession(1);
     this.ussdsession.saveSessionMode(0);
     this.responsemessage = "Notifications:" + this.newLine;
     this.responsemessage += "Invalid option, Are you sure you want to STOP receiving notifications from " + AppConfigHelper.APP_NAME + this.newLine;
     this.responsemessage += AppConfigHelper.confirmActionText + this.newLine;
     return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
   }
 }
