 package LuseDma.ussd.views.demoViews;
 
import java.util.Map;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.FormSession;
import org.json.JSONObject;
 

 public class RepaymentViews
 {
   private String newLine = String.format("%n", new Object[0]);
   
   public  static final String FORM_NAME = "repayment_form";
   public  static final String LOAN_ACCOUNT = "loan_account";
   public String getPayReturnView(FormSession session, String action, boolean err) {
     String amountToPay, payingMSISDN; 
     JSONObject selected;
     String view = "", formname = "repayment_form";
     Map form,loanAccount;
     switch (action) {
       case "no_loan":
            view = view + "Repayment:" + this.newLine;
            view = view + "Sorry you have no loans to repay." + this.newLine;
            view = view + "Press;" + this.newLine;
            view = view + "1. To borrow" + this.newLine;
            AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
            return view;
       case "enter_amount":
            form = session.getForm(FORM_NAME);
            selected = new JSONObject(form);
            view = view + "Repayment:" + this.newLine;
            view = view + "Loan: " + selected.optString("loan_name") + this.newLine;
            //view = view + "Paid: " + AppConfigHelper.formatAmount(selected.optString("repaid")) + this.newLine;
            view = view + "Balance: " + AppConfigHelper.formatAmount(selected.optString("outstanding")) + this.newLine;
            if (err) {
                view = view + "Enter amount to pay? Invalid amount less than the balance!" + this.newLine;
            } else {
                view = view + "Enter amount to pay?" + this.newLine;
            }
            AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
            return view;
       case "pay_with_confirmation":
            amountToPay = (String)session.getForm(formname).get("amount_to_pay");
            view = view + "Pay with:" + this.newLine;
            view = view + "Amount:" + AppConfigHelper.formatAmount(amountToPay) + this.newLine;
            if (err) {
               view = view + "Press 1 to pay with own number or Enter other number e.g(0966xxxxxx), Invalid entry" + this.newLine;
            } else {
               view = view + "Press 1 to pay with own number or Enter other number e.g(0966xxxxxx)" + this.newLine;
            }
            view = view + AppConfigHelper.getGoBackText();
            return view;
       case "confirmation":
            payingMSISDN = (String)session.getForm(formname).get("paying_msisdn");
            amountToPay = (String)session.getForm(formname).get("amount_to_pay");
            view = view + "Review;" + this.newLine;
            view = view + "No.: " + payingMSISDN + this.newLine;
            view = view + "Amount: " + AppConfigHelper.formatAmount(amountToPay) + this.newLine;
            view = view + "Press 1 to confirm" + this.newLine;
            AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
            //System.out.println((new Gson()).toJson(form));
            return view;
       case "transaction_success":
            payingMSISDN = (String)session.getForm(formname).get("paying_msisdn");
            amountToPay = (String)session.getForm(formname).get("amount_to_pay");
            view = view + "Successfully Initiated:" + this.newLine;
            view = view + "No.: " + payingMSISDN + this.newLine;
            view = view + "Amount: " + AppConfigHelper.formatAmount(amountToPay) + this.newLine;
            view = view + "Please check your MOMO Account to approve the Payment" + this.newLine;
            return view;
     } 
     view = view + "Sorry your transaction was not successful, please try again later" + this.newLine;
     view = view + "Press;" + this.newLine;
     view = view + "1. To try this again" + this.newLine;
     view = view + AppConfigHelper.getGoBackText();
     return view; 
   }
   
   
   
 }

