 package LuseDma.ussd.views.demoViews;
 
 import java.util.ArrayList;

 import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.Account;
 import LuseDma.ussd.pojos.ListItem;
 import LuseDma.ussd.pojos.kyc.Subscriber;
 import org.json.JSONObject;
 

 public class AccountViews
 {
   private String newLine = String.format("%n", new Object[0]);
   public String getAccountMenuView(boolean action) {
     String view = "Account:Press;" + this.newLine;
     if (action) {
       view = view + "Invalid option." + this.newLine;
     }
     view = view + "1.Account" + this.newLine;
     view = view + "2.Balance" + this.newLine;
     view = view + "3.Change pin" + this.newLine;
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
     } 
     return view;
   }
   public ArrayList<ListItem> getBalanceDetailsView(Account account) {
     ListItem item;
     ArrayList<ListItem> view = new ArrayList<>();
         item = new ListItem("Loan", AppConfigHelper.parseNull(account.getLoanName()));
         view.add(item);
         item = new ListItem("Borrowed", AppConfigHelper.formatAmount(account.getPrincipal().toString()));
         view.add(item);
         item = new ListItem("Interest", AppConfigHelper.formatAmount(account.getInterest().toString()));
         view.add(item);
         item = new ListItem("Outstanding", AppConfigHelper.formatAmount(account.getOutstanding().toString()));
         view.add(item);
         item = new ListItem("Paid", AppConfigHelper.formatAmount(account.getRepaid().toString()));
         view.add(item);
         item = new ListItem("Date Borrowed", AppConfigHelper.unwrapDate(account.getDateBorrowed(),"dd-MM-yyyy"));
         view.add(item);
         if(account.getOwing()){
            item = new ListItem("Due Date", AppConfigHelper.unwrapDate(account.getDateDue(),"dd-MM-yyyy"));
            view.add(item);
            item = new ListItem("Status", "Owing");
            view.add(item);
         } else {
            item = new ListItem("Date Paid", AppConfigHelper.unwrapDate(account.getDateDue(),"dd-MM-yyyy"));
            view.add(item);
            item = new ListItem("Status", "Paid");
            view.add(item);
         }
         return view;
   }
   public ArrayList<ListItem> getSubscriberDetailsView(Subscriber subscriber) {
     ListItem item;
     ArrayList<ListItem> view = new ArrayList<>();
         item = new ListItem("ID", AppConfigHelper.parseNull(subscriber.getIdnumber()));
         view.add(item);
         item = new ListItem("Type", AppConfigHelper.parseNull(subscriber.getPersonalDetails().getIdType()));
         view.add(item);
         item = new ListItem("Employer", AppConfigHelper.parseNull(subscriber.getIncomeDetails().getEmployer()));
         view.add(item);
         item = new ListItem("Max Loan", AppConfigHelper.parseNull(subscriber.getIncomeDetails().getMaximumOffer()));
         //view = view + "Credit Score: " + AppConfigHelper.formatAmount(selected.optString("credit_score")) + this.newLine;
         view.add(item);
         item = new ListItem("Title", AppConfigHelper.capitalizeFirstLetter(subscriber.getPersonalDetails().getTitle()));
         view.add(item);
         item = new ListItem("Firstname", AppConfigHelper.capitalizeFirstLetter(subscriber.getPersonalDetails().getFirstname()));
         view.add(item);
         item = new ListItem("Lastname", AppConfigHelper.capitalizeFirstLetter(subscriber.getPersonalDetails().getLastname()));
         view.add(item);
         item = new ListItem("Sex", AppConfigHelper.parseNull(subscriber.getPersonalDetails().getGender()));
         view.add(item);
         item = new ListItem("DOB", AppConfigHelper.formatDate(subscriber.getPersonalDetails().getDateOfBirth(),"dd-MM-yyyy"));
         view.add(item);
         item = new ListItem("Phone", AppConfigHelper.capitalizeFirstLetter(subscriber.getMsisdn()));
         view.add(item);
         item = new ListItem("Email", AppConfigHelper.capitalizeFirstLetter(subscriber.getContactDetails().getEmail()));
         view.add(item);
         item = new ListItem("Next Of Kin", AppConfigHelper.capitalizeFirstLetter(subscriber.getNextOfKin().getFirstname()) + " " + AppConfigHelper.capitalizeFirstLetter(subscriber.getNextOfKin().getLastname()));
         view.add(item);
         return view;
   }

   public String getGenericDetailsView(JSONObject selected, String action) {
     String name, view = "";
     switch (action) {
       case "loan_statement_details":
         view = view + "Loan:" + this.newLine;
         view = view + "Due Date: " + selected.optString("PRD_PR_YEAR", "") + "/" + selected.optString("PRD_PR_PERIOD", "") + this.newLine;
         view = view + "Employee: " + AppConfigHelper.formatAmount(selected.optString("PRD_AMT_EYEE")) + this.newLine;
         view = view + "Employer: " + AppConfigHelper.formatAmount(selected.optString("PRD_AMT_EYER")) + this.newLine;
         view = view + "Total: " + AppConfigHelper.formatAmount(selected.optString("TOTAL_CONTR")) + this.newLine;
         view = view + AppConfigHelper.getOneStepBackText();
         return view;
       case "repayment_statement_details":
         view = view + "Contribution:" + this.newLine;
         view = view + "Period: " + selected.optString("period") + this.newLine;
         view = view + "Employee: " + AppConfigHelper.formatAmount(selected.optString("ITEM_AMT")) + this.newLine;
         view = view + "Employer: " + AppConfigHelper.formatAmount(selected.optString("SURCHARGE_AMT")) + this.newLine;
         view = view + "Total: " + AppConfigHelper.formatAmount(selected.optString("TOT")) + this.newLine;
         view = view + AppConfigHelper.getOneStepBackText();
         return view;
     } 
     
     return view;
   }
 }

