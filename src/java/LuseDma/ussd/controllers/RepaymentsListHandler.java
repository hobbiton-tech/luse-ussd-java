 package LuseDma.ussd.controllers;
 
 import java.util.ArrayList;

 import LuseDma.ussd.common.JsonHelper;
 import LuseDma.ussd.helpers.PaginationHelper;
 import LuseDma.ussd.helpers.USSDSession;
 import LuseDma.ussd.models.LoansLedgerModel;
 import LuseDma.ussd.views.demoViews.LoanViews;
 import LuseDma.ussd.pojos.ListItem;
 import LuseDma.ussd.pojos.demo.LoanItem;
import LuseDma.ussd.pojos.demo.LoanPayment;
 import LuseDma.ussd.pojos.MobileSession;
 import LuseDma.ussd.pojos.SelectedOption;
 import LuseDma.ussd.pojos.USSDResponse;
 import com.google.gson.Gson;

 public class RepaymentsListHandler
 {
   private final int handlersessionlevel = 3;
   private USSDSession ussdsession = null;
   private MobileSession mobilesession = null;
   LoanItem loanItem = null;
   private LoanPayment paymentItem = null;
   private String header;
   private PaginationHelper paginationHelper;
   
   private ArrayList<ListItem> listitems = null;
   private ArrayList<LoanPayment> payments = null;
   private SelectedOption option;
   private String msisdn;
   
   private LoansLedgerModel model = null;
   private LoanViews view = new LoanViews();
   private String newLine = String.format("%n", new Object[0]);
   public RepaymentsListHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
     this.mobilesession = ussdsession.getMobileSession();
     this.msisdn = this.ussdsession.getMSISDN();
     this.model = new LoansLedgerModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
     
     loanItem = JsonHelper.<LoanItem>mapToObject(this.mobilesession.getSelectedOption().getItem(),LoanItem.class);
     this.payments = loanItem.getLoanPayments();
   }
   public USSDResponse runSession() {
     if (this.ussdsession.getSessionLevel() < handlersessionlevel + 2) {
       LoanPayment selected; Integer index; 
       this.header = "Payments:"+ loanItem.getLoanProduct().getName()+ this.newLine;
       
       this.listitems = getListItems();
       PaginationHelper paginationHelper = null;
       switch (this.ussdsession.getSessionLevel()) {
         case handlersessionlevel:
           this.ussdsession.resetList();
           this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
           this.ussdsession.saveSessionMode(1);
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "You have no employees.");
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
         case handlersessionlevel + 1:
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "You have no employees.");
           switch (paginationHelper.getListPageActionHandle()) {
             case 1:
               index = Integer.valueOf(Integer.parseInt(this.ussdsession.getUserInput()) - 1);
               selected = getSelectedItem(index);
               this.option = this.mobilesession.getSelectedOption();
               this.option.setIndex(index);
               this.option.setName(selected.getReference());
               this.ussdsession.saveSelectedOption(this.option);
                
               this.ussdsession.resetList();
               this.ussdsession.saveSessionMode(1);
               this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
               return runViewerOrFormSession();
             case 6:
               this.ussdsession.resetList();
               this.ussdsession.saveSessionMode(2);
               this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
               return runViewerOrFormSession();
           } 
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
       } 
       return runViewerOrFormSession();
     } 
     return runViewerOrFormSession();
   }
   public USSDResponse runViewerOrFormSession() {
        LoanPayment selected;
        this.ussdsession.saveSessionMode(1);
        selected = payments.get(this.mobilesession.getSelectedOption().getIndex());
        System.out.println("Payment:"+new Gson().toJson( selected ));
        return this.ussdsession.buildUSSDResponse(this.view.getPaymentDetailsView(selected), 2);
   }
   
   //loan
   public LoanPayment getSelectedItem(int id) {
     LoanPayment listitem = this.payments.get(Integer.parseInt(this.ussdsession.getUserInput()) - 1);
     return listitem;
   }
   public ArrayList<ListItem> getListItems() {
     ArrayList<ListItem> list = new ArrayList<>();
     for (int i = 0; i < this.payments.size(); i++) {
       LoanPayment item = this.payments.get(i);
       ListItem listitem = new ListItem(item.getReference(), item.getAmount().toString());
       list.add(listitem);
     } 
     return list;
   }
 }

