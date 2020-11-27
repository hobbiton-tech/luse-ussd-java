 package LuseDma.ussd.views.demoViews;
 
 import LuseDma.ussd.common.AppConfigHelper;

 public class ErrorViews
 {
   private String newLine = String.format("%n", new Object[0]);
   
   public String getStartUpView(int level, boolean action) {
     String view = "";
     switch (level) {
       case 2:
         view = view + "Welcome to " + AppConfigHelper.APP_NAME + ":" + this.newLine;
         view = view + "Invalid option" + this.newLine;
         view = view + "Register for " + AppConfigHelper.APP_NAME + " for free? By selecting yes, you agree to the terms and conditions at www.company.com" + this.newLine;
         view = view + "1. Yes" + this.newLine;
         view = view + "2. No" + this.newLine;
         return view;
       case 3:
         view = view + "Thank you for visiting " + AppConfigHelper.APP_NAME + ", We hope to see you again soon, by visiting www.company.com or dialing *747#" + this.newLine;
         return view;
     } 
     view = view + "Welcome to " + AppConfigHelper.APP_NAME + ":" + this.newLine;
     view = view + "Register for " + AppConfigHelper.APP_NAME + " for free? By selecting yes, you agree to the terms and conditions at www.company.com" + this.newLine;
     view = view + "1. Yes" + this.newLine;
     view = view + "2. No" + this.newLine;
     return view;
   }
   
   public String getUnavailableView() {
     String view = AppConfigHelper.APP_NAME + ":" + this.newLine;
     view = view + "This service is temporarily unavailable." + this.newLine;
     view = view + "Please try again later." + this.newLine;
     return view;
   }
   public String getDatabaseFailedView() {
     String view = AppConfigHelper.APP_NAME + ":" + this.newLine;
     view = view + "Failed to connect to database." + this.newLine;
     view = view + "Please try again later." + this.newLine;
     return view;
   }
   public String getProviderResolutionFailedView() {
     String view = AppConfigHelper.APP_NAME + ":" + this.newLine;
     view = view + "Failed to resolve mobile number providerrr." + this.newLine;
     view = view + "Please try again later." + this.newLine;
     return view;
   }
   public String getSessionExpiredView() {
     String view = AppConfigHelper.APP_NAME + ":" + this.newLine;
     view = view + "This session is expired." + this.newLine;
     view = view + "Please try again later." + this.newLine;
     return view;
   }
   public String getBlockedView() {
     String view = AppConfigHelper.APP_NAME + ":" + this.newLine;
     view = view + "Sorry access to this service has been temporarily sususpended.!" + this.newLine;
     view = view + "Please visit our nearest office for assistance." + this.newLine;
     return view;
   }
   public String getGoodbyeView() {
        String view = AppConfigHelper.APP_NAME + ":" + this.newLine;
        view = view + "Goodbye, Thank you for using " + AppConfigHelper.getAppName() + this.newLine;
        view = view + "Hope we were helpful." + this.newLine;
        return view;
   }

 }


