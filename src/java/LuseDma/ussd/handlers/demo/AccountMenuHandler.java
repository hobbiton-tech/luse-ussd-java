 package LuseDma.ussd.handlers.demo;
 
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.views.demoViews.AccountViews;

 
 public class AccountMenuHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private  AccountViews view = new  AccountViews();
   public AccountMenuHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
   }
   public USSDResponse runSession() {
      switch (this.ussdsession.getSessionLevel()) {
        case handlersessionlevel:
            this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
            this.ussdsession.saveSessionMode(0);
            return this.ussdsession.buildUSSDResponse(this.view.getAccountMenuView(false), 2);
      }
      return runOptionSelectedHandler(); 
   } 
   public USSDResponse runOptionSelectedHandler() { 
        AccountViewerHandler accountViewerHandler;
        BalanceViewerHandler balanceViewerHandler;
        AuthHandler authHandler; 
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
          case 1:
            accountViewerHandler = new AccountViewerHandler(this.ussdsession);
            return accountViewerHandler.runSession();
          case 2:
            balanceViewerHandler = new BalanceViewerHandler(this.ussdsession);
            return balanceViewerHandler.runSession();
          case 3:
            this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
            authHandler = new AuthHandler(this.ussdsession);
            return authHandler.runPinChangeSession();
        } 
        return this.ussdsession.buildUSSDResponse(this.view.getAccountMenuView(true), 2); 
   }
 
 }
