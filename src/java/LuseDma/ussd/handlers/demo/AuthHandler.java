 package LuseDma.ussd.handlers.demo;
 
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.handlers.MainMenuHandler;
 import LuseDma.ussd.helpers.USSDSession;

 import java.io.FileNotFoundException;


 public class AuthHandler
 {
   private USSDSession ussdsession = null;
   private String responsemessage = "";
   private MainMenuHandler MainMenuHandler = null;
   private String newLine = String.format("%n", new Object[0]);
   
   public AuthHandler(USSDSession ussdsession) { this.ussdsession = ussdsession; }
   
   public USSDResponse runPinSetupSession() throws FileNotFoundException {
     switch (this.ussdsession.getSecurity().getAuthSessionLevel()) {
       case -1:
         this.ussdsession.saveAuthSession(0);
         this.ussdsession.saveSessionMode(1);
         this.responsemessage = "Setup PIN:" + this.newLine;
         this.responsemessage += getPinForm();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       case 0:
         if (this.ussdsession.isValidPINSchema().booleanValue()) {
           this.ussdsession.getSecurity().setPin(AppConfigHelper.securityHash(this.ussdsession.getUserInput(), "MD5"));
           this.ussdsession.saveAuthSession(1);
           this.ussdsession.saveSessionMode(1);
           this.responsemessage = "Setup PIN:" + this.newLine;
           this.responsemessage += getPinRepeatForm();
           return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
         } 
         this.ussdsession.saveAuthSession(0);
         this.responsemessage = "Setup PIN:" + this.newLine;
         this.responsemessage += "Invalid PIN length!!!" + this.newLine;
         this.responsemessage += getPinForm();
         AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       
       case 1:
         if (this.ussdsession.isValidRepeatPin().booleanValue()) {
           this.ussdsession.getSecurity().setIsLoggedIn(1);
           this.ussdsession.getSecurity().setIsPinSetupMode(0);
           this.ussdsession.saveAuthSession(-1);
 
           
           this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
           return this.MainMenuHandler.runSession();
         } 
         this.ussdsession.saveAuthSession(0);
         this.ussdsession.saveSessionMode(1);
         this.responsemessage = "Setup PIN:" + this.newLine;
         this.responsemessage += "PIN verification failed, PINs do not match!!!" + this.newLine;
         this.responsemessage += getPinForm();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } 
     this.ussdsession.saveAuthSession(-1);
     
     this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
     return this.MainMenuHandler.runSession();
   }
   
   private String getPinForm() {
     String response = "Please enter your new PIN!" + this.newLine;
     response = response + "This PIN will be used for security when accessing your account:" + this.newLine;
     AppConfigHelper.getInstance(); AppConfigHelper.getInstance(); response = response + AppConfigHelper.getMinPinLength() + " to " + AppConfigHelper.getMaxPinLength() + " characters" + this.newLine;
     return response;
   }
   private String getPinRepeatForm() {
     String response = "Please retype new PIN to confirm this PIN setup operation:" + this.newLine;
     return response;
   }
   
   public USSDResponse runSignInSession() throws FileNotFoundException {
     switch (this.ussdsession.getSecurity().getAuthSessionLevel()) {
       case -1:
         this.ussdsession.saveAuthMode(1);
         this.ussdsession.saveAuthSession(0);
         this.ussdsession.saveSessionMode(1);
         this.responsemessage = "Sign In:" + this.newLine;
         this.responsemessage += getSignInForm();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       case 0:
         if (this.ussdsession.getUserInput().equalsIgnoreCase(AppConfigHelper.getPinResetCharacter())) {
           this.ussdsession.saveAuthSession(-1);
           this.ussdsession.saveIsPinResetMode(1);
           return runPinResetSession();
         } 
         if (this.ussdsession.isValidCurrentPin(this.ussdsession.getUserInput()).booleanValue()) {
           this.ussdsession.saveAuthMode(0);
           this.ussdsession.getSecurity().setIsLoggedIn(1);
           this.ussdsession.saveAuthSession(-1);
           
           this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
           return this.MainMenuHandler.runSession();
         } 
         
         this.ussdsession.saveAuthSession(0);
         this.ussdsession.saveSessionMode(1);
         this.responsemessage = "Sign In:" + this.newLine;
         this.responsemessage += "Wrong PIN!!!" + this.newLine;
         
         this.responsemessage += getSignInForm();
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } 
     this.ussdsession.saveAuthSession(-1);
     
     this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
     return this.MainMenuHandler.runSession();
   }
   
   private String getSignInForm() {
     String response = "Please enter your PIN!" + this.newLine;
     response = response + AppConfigHelper.getPinResetText();
     return response;
   }
 
   
   public USSDResponse runPinResetSession() throws FileNotFoundException {
     switch (this.ussdsession.getSecurity().getAuthSessionLevel()) {
       case -1:
         /*if (checkIfVerified()) {
           this.ussdsession.saveAuthSession(0);
           this.ussdsession.saveSessionMode(1);
           this.responsemessage = "Reset PIN:" + this.newLine;
           this.responsemessage += "Please enter your SSN/NRC/Account Number" + this.newLine;
           return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
         } */
         this.ussdsession.saveAuthSession(-1);
         this.ussdsession.saveIsPinSetupMode(1);
         this.ussdsession.saveIsPinResetMode(0);
         return runPinSetupSession();
       
       case 0:
         /*if (checkResetID(this.ussdsession.getUserInput())) {
           this.ussdsession.saveAuthSession(-1);
           this.ussdsession.saveIsPinSetupMode(1);
           this.ussdsession.saveIsPinResetMode(0);
           return runPinSetupSession();
         } */
         this.ussdsession.saveAuthSession(0);
         this.responsemessage = "Reset PIN:" + this.newLine;
         this.responsemessage += "Wrong reset number entered!!!" + this.newLine;
         this.responsemessage += "Please enter your SSN/NRC/Account Number again." + this.newLine;
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } 
     this.ussdsession.saveAuthSession(-1);
     
     this.MainMenuHandler = new MainMenuHandler(this.ussdsession);
     return this.MainMenuHandler.runSession();
   }
   
   public USSDResponse runPinChangeSession() {
     switch (this.ussdsession.getSecurity().getAuthSessionLevel()) {
       case -1:
         this.ussdsession.saveAuthSession(0);
         this.ussdsession.saveSessionMode(1);
         this.responsemessage = "Change PIN:" + this.newLine;
         this.responsemessage += "Please enter your current PIN" + this.newLine;
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       case 0:
         if (this.ussdsession.isValidCurrentPin(this.ussdsession.getUserInput()).booleanValue()) {
           this.ussdsession.saveAuthSession(1);
           this.ussdsession.saveSessionMode(1);
           this.responsemessage = "Change PIN:" + this.newLine;
           this.responsemessage += "Please enter your new PIN" + this.newLine;
           AppConfigHelper.getInstance(); AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getMinPinLength() + " to " + AppConfigHelper.getMaxPinLength() + " characters" + this.newLine;
           return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
         } 
         this.ussdsession.saveAuthSession(0);
         this.responsemessage = "Change PIN:" + this.newLine;
         this.responsemessage += "PIN verification failed, wrong PIN entered!!!" + this.newLine;
         this.responsemessage += "Please enter your current PIN again." + this.newLine;
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
       
       case 1:
         if (this.ussdsession.isValidPINSchema().booleanValue()) {
           this.ussdsession.getSecurity().setPin(AppConfigHelper.securityHash(this.ussdsession.getUserInput(), "MD5"));
           this.ussdsession.saveAuthSession(2);
           this.responsemessage = "Change PIN:" + this.newLine;
           this.responsemessage += "Please retype your new PIN to confirm this PIN change operation:" + this.newLine;
           return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
         } 
         this.ussdsession.saveAuthSession(1);
         this.responsemessage = "Change PIN:" + this.newLine;
         this.responsemessage = "Invalid pin length!!!" + this.newLine;
         this.responsemessage += "Please enter your new pin again," + this.newLine;
         AppConfigHelper.getInstance(); AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getMinPinLength() + " to " + AppConfigHelper.getMaxPinLength() + " characters" + this.newLine;
         return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } 
     
     if (this.ussdsession.isValidRepeatPin().booleanValue()) {
       this.ussdsession.saveAuthSession(-1);
       this.responsemessage = "Change PIN:" + this.newLine;
       this.responsemessage += "Your PIN code was saved successfully" + this.newLine;
       AppConfigHelper.getInstance(); this.responsemessage += AppConfigHelper.getGoBackText();
       return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
     } 
     this.ussdsession.saveAuthSession(2);
     this.responsemessage = "Change PIN:" + this.newLine;
     this.responsemessage += "PIN verification failed, PINs do not match!!!" + this.newLine;
     this.responsemessage += "Please retype new PIN to confirm this PIN change operation:" + this.newLine;
     return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
   }
}
