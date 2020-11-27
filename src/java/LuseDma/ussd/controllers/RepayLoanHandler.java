 package LuseDma.ussd.controllers;

 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.views.demoViews.RepaymentViews;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.common.BrivoHelper;
 import LuseDma.ussd.handlers.demo.LoanOfferHandler;
 import LuseDma.ussd.helpers.USSDSession;
 import LuseDma.ussd.http.RemoteDataHelper;
 import LuseDma.ussd.models.LoansLedgerModel;
 import LuseDma.ussd.pojos.Account;
 import LuseDma.ussd.pojos.FormSession;
 import LuseDma.ussd.pojos.ListItem;
 import LuseDma.ussd.pojos.demo.LoanItem;
 import LuseDma.ussd.pojos.demo.LoanPayment;
 import LuseDma.ussd.pojos.MobileSession;
 import LuseDma.ussd.pojos.SelectedOption;

 import java.util.Date;

 import org.json.JSONObject;

 public class RepayLoanHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private MobileSession mobilesession = null;
   private FormSession formsession = null;
   private ArrayList<ListItem> listitems = null;
   private String header = null;
   
   private Map<String, Object> form;
   private SelectedOption option;
   private String fullname = "";
   private String msisdn;
   private RepaymentViews view = new RepaymentViews();
   private Account account;
   public RepayLoanHandler(USSDSession ussdsession) {
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.fullname = this.mobilesession.getName().getFullname();
        this.msisdn = this.ussdsession.getMSISDN();
        this.account = this.mobilesession.getAccount();
        this.form = this.mobilesession.getFormSession().getForm(RepaymentViews.FORM_NAME);
   }
   public USSDResponse runSession() {
        this.formsession.getFormData().put(RepaymentViews.FORM_NAME, this.form);
        this.ussdsession.saveFormSession(this.formsession);
        String input = "";
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                if(this.account.getOwing()){
                    this.formsession.clearFormData(RepaymentViews.FORM_NAME);
                    this.ussdsession.saveFormSession(this.formsession);
                    
                    this.form.put("loan_name",this.account.getLoanName());
                    this.form.put("repaid",this.account.getRepaid());
                    this.form.put("outstanding",this.account.getOutstanding());

                    this.formsession.getFormData().put(RepaymentViews.FORM_NAME, this.form);
                    this.ussdsession.saveFormSession(this.formsession);

                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    this.ussdsession.saveSessionMode(2);
                    return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "enter_amount", false), 2);
                } else {
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    this.ussdsession.saveSessionMode(2);
                    return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "no_loan", true), 2);
                }
            case handlersessionlevel + 1:
              input = this.ussdsession.getUserInput();
              if(this.account.getOwing()){
                    if (validateAmount(input)) {
                      Double amountEntered = Double.parseDouble(input);
                      if(amountEntered <= this.account.getOutstanding()){
                        this.form.put("amount_to_pay", formatAmount(input));
                        this.formsession.getFormData().put(RepaymentViews.FORM_NAME, this.form);
                        this.ussdsession.saveFormSession(this.formsession);

                        this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                        this.ussdsession.saveSessionMode(2);
                        return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "pay_with_confirmation", false), 2);
                      }
                      return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "enter_amount", true), 2);
                    } else {
                      return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "enter_amount", true), 2);
                    }
              } else {
                if(input.equalsIgnoreCase("1")){
                    this.ussdsession.setSessionLevelOption(0, 1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel);
                    LoanOfferHandler loanOfferHandler = new LoanOfferHandler(this.ussdsession);
                    return loanOfferHandler.runSession();
                } else {
                   return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "no_loan", true), 2);
                }
              }
            case handlersessionlevel + 2:
                input = this.ussdsession.getUserInput();
                JSONObject payingNumber =  getPayingNumber(input,this.ussdsession.getMSISDN());
                if (payingNumber.optBoolean("is_valid", false)) {
                    this.form.put("paying_msisdn", payingNumber.optString("msisdn", this.ussdsession.getMSISDN()));
                    this.formsession.getFormData().put(RepaymentViews.FORM_NAME, this.form);
                    this.ussdsession.saveFormSession(this.formsession);
                    
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
                    this.ussdsession.saveSessionMode(2);
                    return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "confirmation", false), 2);
                } else {
                    return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "pay_with_confirmation", true), 2);
                }
            case handlersessionlevel + 3:
                input = this.ussdsession.getUserInput();
                if (this.ussdsession.isConfirmedAction("1").booleanValue()) {
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
                    this.ussdsession.saveSessionMode(1);
                    return runTransactionSession();
                } else {
                    return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "confirmation", true), 2);
                }
        } 
        return runTransactionSession();
   }

   public USSDResponse runTransactionSession() {
     switch (this.ussdsession.getSessionLevel()) {
       case handlersessionlevel + 4:
         try {
           HashMap form = (HashMap)this.mobilesession.getFormSession().getFormData().get(RepaymentViews.FORM_NAME);
           String payingMSISDN = (String)form.get("paying_msisdn");
           String amountToPay = (String)form.get("amount_to_pay");
           String mno = AppConfigHelper.getMSISDNProvider(payingMSISDN);
           HashMap<String, String> params = new HashMap<>();
           params.put("mno", mno);
           params.put("mobile", payingMSISDN);
           params.put("amount", amountToPay);
           params.put("delay", AppConfigHelper.MOMO_PAYMENT_DELAY);
           JSONObject resp = RemoteDataHelper.runRepayment(params);
           if (resp.has("statusCode") && !resp.isNull("statusCode")) {
             if (resp.optString("statusCode").equalsIgnoreCase("SUCCESS")) {
                 
                Double amountPaid = Double.parseDouble(amountToPay);
                Double priorBalance = this.account.getOutstanding();
                Double outstanding = priorBalance - amountPaid;
                Double repaid = this.account.getRepaid() + amountPaid;
                this.account.setOutstanding(outstanding);
                this.account.setRepaid(repaid);
                if(outstanding == 0.0){
                   this.account.setOwing(false);
                }
                this.ussdsession.saveAccount(account);
                
                LoansLedgerModel ledger = new LoansLedgerModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
                LoanItem loan =  ledger.findByReference(this.account.getReference());
                loan.setOutstanding(outstanding);
                loan.setRepaid(repaid);
                loan.setDatePaid(new Date());
                if(outstanding == 0.0){
                   loan.setStatus(1);
                }
                
                LoanPayment payment = new LoanPayment();
                payment.setReference(BrivoHelper.uniqueCurrentTimeMs().toString());
                payment.setChannel("Self");
                payment.setAmount(amountPaid);
                payment.setOutstanding(priorBalance);
                payment.setBalance(outstanding);
                payment.setPaymentDate(new Date());
                loan.getLoanPayments().add(payment);
                
                ledger.update(loan);
                        
                        
                this.ussdsession.saveFormSession(this.formsession);
                this.ussdsession.saveSessionMode(1);
                this.ussdsession.saveUSSDSession(handlersessionlevel);
                return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "transaction_success", false), 3);
             } 
             this.ussdsession.saveUSSDSession(handlersessionlevel);
             this.ussdsession.saveSessionMode(1);
             return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "transaction_failed", false), 2);
           } 
           this.ussdsession.saveUSSDSession(handlersessionlevel);
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(this.view.getPayReturnView(this.formsession, "transaction_failed", false), 2);
         }
         catch (Exception e) {
           AppConfigHelper.logger.error("RepayLoan:runTransactionSession:", e);
           this.ussdsession.saveUSSDSession(4);
           return runSession();
         } 
     } 
     this.ussdsession.saveUSSDSession(handlersessionlevel);
     return runSession();
   }
 
   
  public JSONObject getPayingNumber(String input,String msisdn) {
     JSONObject validate = new JSONObject();
     try{
        if(input.equalsIgnoreCase("1")){
           validate.putOpt("msisdn", msisdn);
           validate.putOpt("is_valid", Boolean.valueOf(true));
        } else {
           HashMap map = AppConfigHelper.getPhoneNumber(input);
           validate.putOpt("msisdn", (String) map.get("msisdn"));
           validate.putOpt("is_valid", (Boolean) map.get("is_valid"));
        }
     } catch(Exception e){}
     return validate;
   }
   public static String formatAmount(String amt) {
     try {
       DecimalFormat formatter = new DecimalFormat("###.##");
       amt = formatter.format(Double.parseDouble(amt));
       return  amt;
     } catch (Exception e) {
       return "0.00";
     } 
   }
   public Boolean validateAmount(String input) {
     try {
       Pattern pattern = Pattern.compile("((\\d{1,7})(((\\.)(\\d{0,2})){0,1}))");
       Matcher matcher = pattern.matcher(input);
       return Boolean.valueOf(matcher.matches());
     } catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
 }

