 package LuseDma.ussd.views.demoViews;
 
 import java.util.Map;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.Account;
 import LuseDma.ussd.pojos.FormSession;
 import java.util.Date;
 import org.json.JSONObject;
 
 public class LoanOfferViews
 {
   private String newLine = String.format("%n", new Object[0]); 
   public  static final String FORM_NAME = "loan_offer_form";
   public  static final String LOAN_OFFER = "loan_offer";
   
   public  String getStillOwingView(Account account,Boolean err) {
        String view = "";
        view = view + "Borrow:" + this.newLine;
        if(err){
            view = view + "Sorry you have an outstanding loan. invalid option" + this.newLine;
        } else {
           view = view + "Sorry you have an outstanding loan." + this.newLine;
        }
        view = view + "Loan: " + account.getLoanName() + this.newLine;
        view = view + "Balance: " + AppConfigHelper.formatAmount(account.getOutstanding().toString()) + this.newLine;
        view = view + "Due Date: " + AppConfigHelper.unwrapDate(account.getDateDue(), "dd-MM-yyyy") + this.newLine;
        view = view + "Press;" + this.newLine;
        view = view + "1. To repay" + this.newLine;
        AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
        return view;
   }
   public String getLoanOfferView(FormSession session, String action, boolean err) { 
     Integer amountToPay; String npin; 
     JSONObject selected;
     String view = "";
     Map form,loanOffer;
     switch (action) {
       case "offer_selection":
            view = view + "Select loan at 10% interest:" + this.newLine;
            return view;
       case "confirmation":
            form = session.getForm(FORM_NAME);
            selected = new JSONObject((Map)form.get(LOAN_OFFER));
            view = view + "Review;" + this.newLine;
            view = view + "Borrow: " + AppConfigHelper.formatAmount(selected.optString("principal")) + this.newLine;
            view = view + "Interest: " + AppConfigHelper.formatAmount(selected.optString("interest")) + this.newLine;
            view = view + "Payback: " + AppConfigHelper.formatAmount(selected.optString("repayment")) + this.newLine;
            Date dueDate = AppConfigHelper.addMinutesToDate(new Date(),selected.optInt("duration"));
            view = view + "Due Date: " + AppConfigHelper.unwrapDate(AppConfigHelper.getLastDateOfTheMonth(), "dd-MM-yyyy") + this.newLine;
            view = view + "Press 1 to confirm" + this.newLine;
            AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
            return view;
       case "transaction_success":
            form = session.getForm(FORM_NAME);
            selected = new JSONObject((Map)form.get(LOAN_OFFER));
            view = view + "Successfully Processed:" + this.newLine;
            view = view + "Interest: " + AppConfigHelper.formatAmount(selected.optString("interest")) + this.newLine;
            view = view + "Payback: " + AppConfigHelper.formatAmount(selected.optString("repayment")) + this.newLine;
            view = view + "Loan: " + AppConfigHelper.formatAmount(selected.optString("principal")) + this.newLine;
            view = view + "Please check your MOMO Account" + this.newLine;
            view = view + AppConfigHelper.getGoBackText();
            return view;
     } 
     view = view + "Sorry your transaction was not successful, please try again later" + this.newLine;
     view = view + "Press;" + this.newLine;
     view = view + "1. To try this again" + this.newLine;
     view = view + AppConfigHelper.getGoBackText();
     return view; 
   }
   
 }

