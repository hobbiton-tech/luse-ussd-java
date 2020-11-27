 package LuseDma.ussd.views.demoViews;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.demo.LoanItem;
import LuseDma.ussd.pojos.demo.LoanPayment;


 public class LoanViews
 {
   private String newLine = String.format("%n", new Object[0]);
   public String getGenericListHeadersView(String action) {
     String view = "";
     switch (action) {
       case "loan_history":
         view = view + "History:" + this.newLine;
         return view;
       case "loan_payments":
         view = view + "Payments:" + this.newLine;
         return view;
     } 
     return view;
   }
   public String getLoanDetailsView(LoanItem selected, String action) {
     String view = "";
     switch (action) {
       case "loan_details":
         view = view + "Loan Details;" + this.newLine;
         //view = view + "Ref#: " + selected.getReference() + this.newLine;
         view = view + "Loan: " + selected.getLoanProduct().getName() + this.newLine;
         view = view + "Date: " + AppConfigHelper.unwrapDate(selected.getDateBorrowed(),"dd-MM-yyyy") + this.newLine;
         if(selected.getStatus() == 1){
            view = view + "Paid: " + AppConfigHelper.unwrapDate(selected.getDateBorrowed(),"dd-MM-yyyy") + this.newLine;
            view = view + "Status: Paid" + this.newLine;
         } else {
            view = view + "Bal:" + AppConfigHelper.formatAmount(selected.getOutstanding().toString()) + this.newLine;
            view = view + "Due:" + AppConfigHelper.unwrapDate(selected.getDateBorrowed(),"dd-MM-yyyy") + this.newLine;
            view = view + "Status: Owing" + this.newLine;
            view = view + AppConfigHelper.getGoBackText();
         }
         if(selected.getLoanPayments().size() > 0){
            view = view + "Press;" + this.newLine;
            view = view + "1. For repayments" + this.newLine;
            view = view + AppConfigHelper.getGoBackCharacter() + " to return" + this.newLine;
         }
         return view;
     } 
     return view;
   }
   public String getPaymentDetailsView(LoanPayment selected) {
        String view = "";
        view = view + "Payment Details;" + this.newLine;
        view = view + "Prior: " + AppConfigHelper.formatAmount(selected.getOutstanding().toString()) + this.newLine;
        view = view + "Amount: " + AppConfigHelper.formatAmount(selected.getAmount().toString()) + this.newLine;
        view = view + "After: " + AppConfigHelper.formatAmount(selected.getBalance().toString()) + this.newLine;
        view = view + "Date: " + AppConfigHelper.unwrapDate(selected.getPaymentDate(),"dd-MM-yyyy") + this.newLine;
        view = view + AppConfigHelper.getOneStepBackText();
        return view;
   }
 }

