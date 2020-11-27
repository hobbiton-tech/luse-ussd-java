 package LuseDma.ussd.handlers.demo;
 
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.handlers.MainMenuHandler;
 import LuseDma.ussd.helpers.USSDSession;

 import java.io.FileNotFoundException;


 public class FeedbackHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private MainMenuHandler MainMenuHandler = null;
   private USSDResponse response = null;
   private String responsemessage = "";
   private String newLine = String.format("%n", new Object[0]);
   
   public FeedbackHandler(USSDSession ussdsession) { this.ussdsession = ussdsession; }
   
   public USSDResponse runSession() throws FileNotFoundException {
     switch (this.ussdsession.getSessionLevel()) {
       case 0:
         this.ussdsession.saveUSSDSession(1);
         
         this.ussdsession.saveSessionMode(0);
         return this.ussdsession.buildUSSDResponse(getCategoryMenu(), 2);
       case 1:
         this.ussdsession.saveUSSDSession(2);
         
         this.ussdsession.saveSessionMode(1);
         this.responsemessage = "Feedback:" + this.newLine;
         this.responsemessage += getFeedbackForm();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       
       case 2:
         this.ussdsession.saveUSSDSession(3);
         
         return runHigherSessionLevelHandler();
     } 
     this.ussdsession.saveUSSDSession(-1);
     this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
     this.response = this.MainMenuHandler.runSession();
     return this.response;
   }
 
   
   public USSDResponse runHigherSessionLevelHandler() {
     //USSDMySQLModel model = new USSDMySQLModel(this.ussdsession.getUSSDSessionHelper().getMySQLDB());
     /*if (this.ussdsession.getUserInput().length() > 8) {
       //if (model.saveFeedback(this.ussdsession.getMSISDN(), this.ussdsession.getSessionLevelOption(1), this.ussdsession.getUserInput())) {
         this.ussdsession.saveSessionMode(0);
         this.responsemessage = "Feedback:" + this.newLine;
         this.responsemessage += "Your feedback has been saved successfully" + this.newLine;
         AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       //} 
       this.ussdsession.saveSessionMode(0);
       this.responsemessage = "Feedback:" + this.newLine;
       this.responsemessage += "Sorry failed to save feedback, please try again later" + this.newLine;
       AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
       return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } */
     
     this.ussdsession.saveSessionMode(1);
     this.responsemessage = "Feedback:" + this.newLine;
     this.responsemessage += "Sorry feedback has to be 8 characters and more." + this.newLine;
     this.responsemessage += getFeedbackForm();
     return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
   }
   
   private String getCategoryMenu() {
     this.responsemessage = "Feedback:" + this.newLine;
     this.responsemessage += "Please select your feedback category." + this.newLine;
     this.responsemessage += "1. Member" + this.newLine;
     this.responsemessage += "2. Employer" + this.newLine;
     this.responsemessage += "3. Benefits" + this.newLine;
     AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
     return this.responsemessage;
   }
   private String getFeedbackForm() {
     String form = "What would you like us to help you with?" + this.newLine;
     AppConfigHelper.getInstance(); form = form + AppConfigHelper.getGoBackText();
     return form;
   }
 }
