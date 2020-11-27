 package LuseDma.ussd.views.demoViews;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.helpers.USSDSession;
  
 public class GenericViews
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
   
   public String getMainMenuView(USSDSession ussdsession, boolean action) {
     String view = "Welcome to " + AppConfigHelper.APP_NAME + ":" + this.newLine;
     
     if (action) {
       view = view + "Invalid option." + this.newLine;
     }
     view = view + "1. Request Loan" + this.newLine;
     view = view + "2. Repay Loan" + this.newLine;
     view = view + "3. Account Info" + this.newLine;
     view = view + "4. Terms n Conditions" + this.newLine;
     view = view + "5. History/Info" + this.newLine;
     return view;
   }

   public String getBalanceMenuView(int action) {
     String view = "Balance: Press " + this.newLine;
     if (action == 0) {
       view = view + "Invalid option." + this.newLine;
     }
     view = view + "1. For Payments" + this.newLine;
     view = view + "2. To repay loan" + this.newLine;
     view = view + AppConfigHelper.getGoBackText();
     return view;
   }
 
    public String getTermAndConditionsView() {
        String view = "";
        view = view + "Terms And Conditions:" + this.newLine;
        view = view + "To view the " + AppConfigHelper.APP_NAME + " terms and conditions, please our website at  " + AppConfigHelper.WEBSITE + "" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
   }
 
    public String getGenericListHeadersView(String action) {
     String view = "";
     switch (action) {
       case "account":
         view = view + "Account:" + this.newLine;
         return view;
       case "summary":
         view = view + "Summary:" + this.newLine;
         return view;
       case "mini_statement":
         view = view + "Statement:" + this.newLine;
         return view;
       case "beneficiaries":
         view = view + "Beneficiaries:" + this.newLine;
         return view;
       case "beneficiaries_select":
         view = view + "Select to Edit:" + this.newLine;
         return view;
     } 
     return view;
   }
   
   public String getFeedbackFormView(String action, Boolean err) {
     String view = "Feedback:" + this.newLine;
     switch (action) {
       case "feedback_form":
         if (err.booleanValue()) {
           view = view + "Sorry feedback too short,try again." + this.newLine;
         }
         view = view + "What would you like us to help you with?" + this.newLine;
         view = view + AppConfigHelper.getGoBackText();
         return view;
       case "transaction_success":
         view = view + "Your feedback has been submitted successfully" + this.newLine;
         view = view + AppConfigHelper.getGoBackText();
         return view;
     } 
     view = view + "Sorry failed to save feedback, please try again later" + this.newLine;
     view = view + AppConfigHelper.getGoBackText();
     return view;
   }
 
 
   
   public String getUnavailableView() { return "This service is temporarily unavailable. Please try again later."; }
 
   
   public String getUnsubscribedView() { return "You have successfully disabled notifications."; }
 
   
   public String getSubscribedView() { return "You have successfully enabled notifications."; }
 
   
   public String getUnSuccessfulView() { return "Your request was not successful. Please try again later."; }
 }


