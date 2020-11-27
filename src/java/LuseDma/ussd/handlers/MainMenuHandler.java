 package LuseDma.ussd.handlers;

 import LuseDma.ussd.helpers.USSDSession;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.views.LuseMianViews;

 import java.io.FileNotFoundException;

 public class MainMenuHandler
 {
   private final int handlersessionlevel = -1;
   private USSDSession ussdsession = null;
     private LuseMianViews view = new LuseMianViews();
   
   public MainMenuHandler(USSDSession ussdsession) { this.ussdsession = ussdsession; }
   
   public USSDResponse runSession() throws FileNotFoundException {
     switch (this.ussdsession.getSessionLevel()) {
       case handlersessionlevel:
         this.ussdsession.saveUSSDSession(handlersessionlevel+1);
         this.ussdsession.saveSessionMode(0);
         return this.ussdsession.buildUSSDResponse(this.view.getMainMenuView(this.ussdsession, false), 2);
     } 
     return runOptionSelectedHandler();
   } 
   
   public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
       RegistrationHandler registrationHandler;
       SecuritiesMenuHandler securitiesMenuHandler;
       switch (this.ussdsession.getSessionLevelOption(handlersessionlevel+1)) {
         case 1:
           securitiesMenuHandler = new SecuritiesMenuHandler(this.ussdsession);
           return securitiesMenuHandler.runSession();
     } 
     return this.ussdsession.buildUSSDResponse(this.view.getMainMenuView(this.ussdsession, true), 2); }
 }
