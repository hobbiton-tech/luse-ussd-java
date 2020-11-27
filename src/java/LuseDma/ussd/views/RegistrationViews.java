 package LuseDma.ussd.views;
 
import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.FormSession;


 public class RegistrationViews
 {
   private String newLine = String.format("%n", new Object[0]);
   
   public  static final String FORM_NAME = "registration_form";
   
   public String getRegistrationView(FormSession session, String action, boolean err) {
     String view = "";
     switch (action) {
       case "title":
         view = view + "Select your title? press;" + this.newLine;
         if (err) {
           view = view + "Sorry invalid choice." + this.newLine;
         }
         view = view + "1. Mr" + this.newLine;
         view = view + "2. Mrs" + this.newLine;
         view = view + "3. Miss" + this.newLine;
         view = view + "4. Ms" + this.newLine;
         view = view + "5. Dr" + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "firstname":
         view = view + "Enter Firstname." + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "middlename":
         view = view + "Enter Other names." + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "lastname":
         view = view + "Enter Surname." + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "idnumber":
         if (err) {
           view = view + "Sorry NRC already exists." + this.newLine;
         }
         view = view + "Enter NRC." + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "dob":
         if (err) {
           view = view + "Sorry invalid date." + this.newLine;
         }
         view = view + "Enter DOB. e.g.(DD/MM/YYYY)" + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "gender":
         view = view + "Select your gender? press;" + this.newLine;
         if (err) {
           view = view + "Sorry invalid choice." + this.newLine;
         }
         view = view + "1. For Male" + this.newLine;
         view = view + "2. For Female" + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "address":
         view = view + "Enter Residential Address." + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "province":
         view = view + "Select Province:" + this.newLine;
         return view;
       case "sector":
         view = view + "Select Your Sector:" + this.newLine;
         return view;
       case "marital_status":
         view = view + "Select your Marital Status? press;" + this.newLine;
         if (err) {
           view = view + "Sorry invalid choice." + this.newLine;
         }
         view = view + "1. Married" + this.newLine;
         view = view + "2. Widowed" + this.newLine;
         view = view + "3. Separated" + this.newLine;
         view = view + "4. Divorced" + this.newLine;
         view = view + "5. Single" + this.newLine;
         AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
         return view;
       case "transaction_success":
         view = view + "Congratulations. Your social security number is " +  (String)session.getForm(FORM_NAME).getOrDefault("ssn", "") + this.newLine;
         view = view + "Visit the nearest NAPSA office for collection of your card, else it will be delivered to the provided address." + this.newLine;
         view = view + "Press;" + this.newLine;
         view = view + "1. For Main Menu" + this.newLine;
         return view;
     } 
     view = view + "Sorry your registration was not successful, please try again later" + this.newLine;
     view = view + "Press;" + this.newLine;
     view = view + "1. To try this again" + this.newLine;
     view = view + AppConfigHelper.getGoBackText();
     return view;
   }
   
   
   
 }

