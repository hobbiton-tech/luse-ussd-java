 package LuseDma.ussd.handlers;
 
 import LuseDma.ussd.helpers.USSDSessionHelper;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.helpers.USSDSession;
 import LuseDma.ussd.views.demoViews.ErrorViews;
 import org.apache.commons.lang.StringUtils;

 import java.io.FileNotFoundException;

 public class USSDEngineCoreHandler
 {
   private USSDSessionHelper mUSSDSessionHelper = null;
   private USSDSession ussdsession = null;
   private MainMenuHandler mMainMenuHandler = null;
   private ErrorViews view = new ErrorViews();
   public USSDEngineCoreHandler(USSDSessionHelper object) { this.mUSSDSessionHelper = object; }
   
   public USSDResponse runUSSDSessionHandler(Integer session_level, Integer session_mode) {
     switch (this.mUSSDSessionHelper.getSessionStatus()) {
       case 1:
         this.mUSSDSessionHelper.setUserInputHandle();
         if (this.mUSSDSessionHelper.saveUSSDSession(session_level.intValue(), session_mode.intValue()).booleanValue())
         {
           return runServiceStart();
         }
         return new USSDResponse(this.mUSSDSessionHelper,this.view.getDatabaseFailedView(),3);
       case 2:
         this.mUSSDSessionHelper.setSessionDirectionHandle();
         return runMSISDNServiceContinue();
       case 3:
         return new USSDResponse(this.mUSSDSessionHelper,this.view.getGoodbyeView(),3);
     } 
     return new USSDResponse(this.mUSSDSessionHelper,this.view.getSessionExpiredView(),3);
   }
   
   private USSDResponse runServiceStart() {
     try {
       this.ussdsession = new USSDSession(this.mUSSDSessionHelper);
       this.ussdsession.resetAuthSession();
       this.ussdsession.setUSSDSession();
       if (this.ussdsession.isBlocked().booleanValue()) {
         return this.ussdsession.buildUSSDResponse(this.view.getBlockedView(), 3);
       } 
       switch (this.mUSSDSessionHelper.getUserInputHandle()) {
         case 1:
           this.mMainMenuHandler = new MainMenuHandler(this.ussdsession);
           return this.mMainMenuHandler.runSession();
         case 2:
           this.ussdsession.setQuickAccessOptions(this.mUSSDSessionHelper.getQuickOptions());
           this.mMainMenuHandler = new MainMenuHandler(this.ussdsession);
           return this.mMainMenuHandler.runSession();
       } 
       return getUnavailable();
     }
     catch (RuntimeException | FileNotFoundException e) {
       AppConfigHelper.logger.error("Exception:", e);
       return getUnavailable();
     } 
   }
   private USSDResponse runMSISDNServiceContinue() {
     try {
       this.ussdsession = new USSDSession(this.mUSSDSessionHelper);
       this.ussdsession.saveLastSeen();
       this.ussdsession.setUSSDSession();
       if (this.ussdsession.isBlocked().booleanValue()) {
         return this.ussdsession.buildUSSDResponse(this.view.getBlockedView(), 3);
       } 
       return runUSSDSession();
     }
     catch (RuntimeException | FileNotFoundException e) {
       AppConfigHelper.logger.error("Exception:", e);
       return getUnavailable();
     } 
   }
   private USSDResponse runUSSDSession() throws FileNotFoundException {
     switch (this.mUSSDSessionHelper.getSessionDirectionHandle()) {
       case 1:
         this.ussdsession.setSessionLevelOption();
         this.mMainMenuHandler = new MainMenuHandler(this.ussdsession);
         return this.mMainMenuHandler.runSession();
       case 2:
         if (this.ussdsession.getSecurity().getIsAuthenticationMode() == 1) 
             this.ussdsession.resetAuthSession(); 
         if (this.ussdsession.getSessionMode() != 3) {
           this.ussdsession.saveUSSDSession(getPriorSessionLevel(2));
         } else if (this.ussdsession.getSessionMode() == 3) {
           this.ussdsession.saveUSSDSession(getPriorSessionLevel(0));
         } 
         this.mMainMenuHandler = new MainMenuHandler(this.ussdsession);
         return this.mMainMenuHandler.runSession();
       case 3:
         this.ussdsession.saveUSSDSession(getPriorSessionLevel(1));
         this.mMainMenuHandler = new MainMenuHandler(this.ussdsession);
         return  this.mMainMenuHandler.runSession();
       case 4:
         if (this.ussdsession.getSecurity().getIsAuthenticationMode() == 1) 
             this.ussdsession.resetAuthSession(); 
         if (this.ussdsession.getSessionMode() != 3) {
           String input = this.mUSSDSessionHelper.getUserInput();
           AppConfigHelper.getInstance(); int back_char_count = StringUtils.countMatches(input, AppConfigHelper.getReturnToListCharacter());
           this.ussdsession.saveUSSDSession(getPriorSessionLevel(back_char_count));
         } else if (this.ussdsession.getSessionMode() != 3) {

           this.ussdsession.saveUSSDSession(getPriorSessionLevel(0));
         } 
         this.mMainMenuHandler = new MainMenuHandler(this.ussdsession);
         return this.mMainMenuHandler.runSession();
     } 
     return getUnavailable();
   }
 
   
   private Long getBackSessionLevel(long levels) {
     long level;
     if (levels == 0L) {
       level = 0L;
     } else {
       level = this.ussdsession.getSessionLevel() - levels;
       if (level <= -1L) {
         level = -1L;
       }
     } 
     return Long.valueOf(level);
   }
   private int getPriorSessionLevel(int levels) {
     int level;
     if (levels == 0) {
       level = 0;
     } else {
       level = this.ussdsession.getSessionLevel() - levels;
       if (level <= -1) {
         level = -1;
       }
     } 
     return level;
   }
   private USSDResponse getSessionExpired() {
     return this.ussdsession.buildUSSDResponse(this.view.getSessionExpiredView(), 3);
   }
   private USSDResponse getUnavailable() {
     return this.ussdsession.buildUSSDResponse(this.view.getUnavailableView(), 3);
   }
 }
