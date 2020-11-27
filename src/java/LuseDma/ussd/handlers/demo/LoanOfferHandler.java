 package LuseDma.ussd.handlers.demo;
 
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.demoViews.LoanOfferViews;
import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.common.BrivoHelper;
import LuseDma.ussd.common.JsonHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.http.RemoteDataHelper;
import LuseDma.ussd.models.LoansLedgerModel;
import LuseDma.ussd.models.LoanProductsModel;
import LuseDma.ussd.pojos.Account;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.demo.LoanItem;
import LuseDma.ussd.pojos.demo.LoanProductsItem;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.SelectedOption;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
 
 public class LoanOfferHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private MobileSession mobilesession = null;
   private FormSession formsession = null;
   
   private ArrayList<ListItem> listitems = null;

   private ArrayList<LoanProductsItem> loanOffers = null;
   
   private String header = null;
   private Map<String, Object> form;
   private SelectedOption option;
   private String msisdn;
   private Account account;
   private LoanProductsModel model = null;
   private LoanOfferViews view = new LoanOfferViews();
   public LoanOfferHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
     this.mobilesession = ussdsession.getMobileSession();
     this.formsession = ussdsession.getMobileSession().getFormSession();
     this.msisdn = this.ussdsession.getMSISDN();
     this.account = this.mobilesession.getAccount();
     this.form = this.formsession.getForm(LoanOfferViews.FORM_NAME);
     this.model = new LoanProductsModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
   }
   
   public USSDResponse runSession() {

     if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 2)) {

        this.header = this.view.getLoanOfferView(this.formsession, "offer_selection", false);
        this.formsession.clearFormData(LoanOfferViews.FORM_NAME);
        this.ussdsession.saveFormSession(this.formsession);
       loanOffers = (ArrayList<LoanProductsItem>)this.model.findAll(this.msisdn);
       this.listitems = getListItems();
       LoanProductsItem selected;


       PaginationHelper paginationHelper = null;
       switch (this.ussdsession.getSessionLevel()) {
         case handlersessionlevel:
           this.ussdsession.resetList();
           this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
           this.ussdsession.saveSessionMode(1);
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "Sorry no information found.");
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
         case handlersessionlevel + 1:
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "Sorry no information found.");
           switch (paginationHelper.getListPageActionHandle()) {
                 case 1:
                   selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1);
                   this.form.put(LoanOfferViews.LOAN_OFFER, JsonHelper.objectToMap(selected));

                   this.formsession.getFormData().put(LoanOfferViews.FORM_NAME, this.form);
                   this.ussdsession.saveFormSession(this.formsession);

                   this.ussdsession.resetList();
                   this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                   return runConfirmationSession();
           } 
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
       } 
       return runConfirmationSession();
     } 
     return runConfirmationSession();
   }
   
   public USSDResponse runConfirmationSession() {
     String input = "";
     switch (this.ussdsession.getSessionLevel()) {
       case handlersessionlevel + 2:
            this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
            this.ussdsession.saveSessionMode(1);
            return this.ussdsession.buildUSSDResponse(this.view.getLoanOfferView(this.mobilesession.getFormSession(), "confirmation", false), 2);
       case handlersessionlevel + 3:
            input = this.ussdsession.getUserInput();
            if (this.ussdsession.isConfirmedAction("1").booleanValue()) {
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
                return runTransactionSession();
            } 
            this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
            this.ussdsession.saveSessionMode(1);
            return this.ussdsession.buildUSSDResponse(this.view.getLoanOfferView(this.mobilesession.getFormSession(), "confirmation", true), 2);
     } 
     return runTransactionSession();
   }
   
   public USSDResponse runTransactionSession() {
         try {
           Map map = (Map)this.mobilesession.getFormSession().getForm(LoanOfferViews.FORM_NAME).get(LoanOfferViews.LOAN_OFFER);
           JSONObject resp = RemoteDataHelper.runLoanOfferPayment(map);
           if (resp.has("statusCode") && !resp.isNull("statusCode")) {
             if (resp.optString("statusCode").equalsIgnoreCase("SUCCESS")) {
                LoanProductsItem product = JsonHelper.<LoanProductsItem>mapToObject(map,LoanProductsItem.class);
                String reference = BrivoHelper.uniqueCurrentTimeMs().toString();
                this.account.setReference(reference);
                this.account.setLoanId(reference);
                this.account.setLoanName(product.getName());
                this.account.setPrincipal(product.getPrincipal());
                this.account.setInterest(product.getInterest());
                this.account.setOutstanding(product.getRepayment());
                this.account.setRepaid(0.0);
                this.account.setOwing(true);
                this.account.setDateBorrowed(new Date());
                //this.account.setDateDue(AppConfigHelper.addDaysToDate(new Date(),product.getDuration()));
                this.account.setDateDue(AppConfigHelper.getLastDateOfTheMonth());
                this.ussdsession.saveAccount(account);
                 
                LoanItem loan = new  LoanItem();
                loan.setReference(reference);
                loan.setMsisdn(this.mobilesession.getMSISDN());
                loan.setLoanProduct(product);
                loan.setOutstanding(product.getRepayment());
                loan.setDateBorrowed(new Date());
                //loan.setDateDue(AppConfigHelper.addDaysToDate(new Date(),product.getDuration()));
                loan.setDateDue(AppConfigHelper.getLastDateOfTheMonth());
                
                loan.setStatus(0);
                
                LoansLedgerModel loansLedgerModel = new LoansLedgerModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
                loansLedgerModel.save(loan);
                 
               this.form.put("completed", Boolean.valueOf(true));
               this.formsession.getFormData().put(LoanOfferViews.FORM_NAME, this.form);
               this.ussdsession.saveFormSession(this.formsession);
               
               this.ussdsession.saveSessionMode(0);
               this.ussdsession.saveUSSDSession(-1);
               return this.ussdsession.buildUSSDResponse(this.view.getLoanOfferView(this.mobilesession.getFormSession(), "transaction_success", false), 2);
             } 
             this.ussdsession.saveUSSDSession(6);
             this.ussdsession.saveSessionMode(0);
             return this.ussdsession.buildUSSDResponse(this.view.getLoanOfferView(this.mobilesession.getFormSession(), "transaction_failed", false), 2);
           } 
           
           this.ussdsession.saveUSSDSession(-1);
           this.ussdsession.saveSessionMode(0);
           return this.ussdsession.buildUSSDResponse(this.view.getLoanOfferView(this.mobilesession.getFormSession(), "transaction_failed", false), 2);
         }
         catch (Exception e) {
           AppConfigHelper.logger.error("LoanOffer:runTransactionSession:", e);
           this.ussdsession.saveSessionMode(0);
           this.ussdsession.saveUSSDSession(-1);
           return runSession();
         } 
   }
 
   
   public LoanProductsItem getSelectedItem(int id) {
     LoanProductsItem item = null;
     try {
       item = this.loanOffers.get(id);
     } catch (Exception e) {}
     return item;
   }
   public ArrayList<ListItem> getListItems() {
     ArrayList<ListItem> list = new ArrayList<>();
     for (int i = 0; i < this.loanOffers.size(); i++) {
       LoanProductsItem item = this.loanOffers.get(i);
       ListItem listitem = new ListItem(item.getId(), item.getName());
       list.add(listitem);
     } 
     return list;
   }
   /*public ArrayList<ListItem> getOffersListItems() {
     ArrayList<ListItem> list = new ArrayList<>();
     try {
       for (int i = 0; i < items.length(); i++) {
         JSONObject o = items.getJSONObject(i);
         String id = o.optString("return_reference", "");
         String name = o.optString("year", "") + "/" + o.optString("month", "");
         ListItem listitem = new ListItem(id, name);
         list.add(listitem);
       } 
     } catch (Exception exception) {}
     return list;
   }*/
 }

