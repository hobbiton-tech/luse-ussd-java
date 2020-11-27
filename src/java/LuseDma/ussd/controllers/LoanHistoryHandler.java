 package LuseDma.ussd.controllers;
 
 import java.util.ArrayList;

 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.JsonHelper;
 import LuseDma.ussd.helpers.PaginationHelper;
 import LuseDma.ussd.helpers.USSDSession;
 import LuseDma.ussd.models.LoansLedgerModel;
 import LuseDma.ussd.pojos.ListItem;
 import LuseDma.ussd.pojos.demo.LoanItem;
 import LuseDma.ussd.pojos.MobileSession;
 import LuseDma.ussd.pojos.SelectedOption;
 import LuseDma.ussd.views.demoViews.LoanViews;

 public class LoanHistoryHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private MobileSession mobilesession = null;
   
   private LoanItem loanItem = null;
   private String header;
   private PaginationHelper paginationHelper;
   
   private ArrayList<ListItem> listitems = null;
   private ArrayList<LoanItem> history = null;
   private SelectedOption option;
   private String msisdn;
   
   private LoansLedgerModel model = null;
   private LoanViews view = new LoanViews();
   public LoanHistoryHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
     this.mobilesession = ussdsession.getMobileSession();
     this.msisdn = this.ussdsession.getMSISDN();
     this.model = new LoansLedgerModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
     this.history = (ArrayList<LoanItem>)this.model.findAll(this.msisdn);
   }
   public USSDResponse runSession() {
     if (this.ussdsession.getSessionLevel() < handlersessionlevel + 2) {
       LoanItem selected; Integer index; 
       this.header = this.view.getGenericListHeadersView("loan_history");
       
       this.listitems = getListItems();
       PaginationHelper paginationHelper = null;
       switch (this.ussdsession.getSessionLevel()) {
         case handlersessionlevel:
           this.ussdsession.resetList();
           this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
           this.ussdsession.saveSessionMode(1);
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "You have no loans.");
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
         case handlersessionlevel + 1:
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "You have no loans.");
           switch (paginationHelper.getListPageActionHandle()) {
             case 1:
               index = Integer.valueOf(Integer.parseInt(this.ussdsession.getUserInput()) - 1);
               selected = getSelectedItem(index.intValue());
               this.option = new SelectedOption(JsonHelper.objectToMap(selected));
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
        String input = "";
        RepaymentsListHandler repaymentsListHandler;
        LoanItem selected = JsonHelper.<LoanItem>mapToObject(this.mobilesession.getSelectedOption().getItem(),LoanItem.class);
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 2:
                this.ussdsession.saveSessionMode(2);
                this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
                return this.ussdsession.buildUSSDResponse(this.view.getLoanDetailsView(selected, "loan_details"), 2);
            case handlersessionlevel + 3:
                input = this.ussdsession.getUserInput();
                if(input.equalsIgnoreCase("1")){
                    repaymentsListHandler = new RepaymentsListHandler(this.ussdsession);
                    return repaymentsListHandler.runSession();
                } else {
                    return this.ussdsession.buildUSSDResponse(this.view.getLoanDetailsView(selected, "loan_details"), 2);
                }
            default:
                repaymentsListHandler = new RepaymentsListHandler(this.ussdsession);
                return repaymentsListHandler.runSession();
        } 
        //this.ussdsession.saveSessionMode(1);
        //return this.ussdsession.buildUSSDResponse(this.view.getLoanDetailsView(selected, "loan_details"), 2);
   }
   
   //loan
   public LoanItem getSelectedItem(int id) {
     LoanItem listitem = this.history.get(Integer.parseInt(this.ussdsession.getUserInput()) - 1);
     return listitem;
   }
   public ArrayList<ListItem> getListItems() {
     ArrayList<ListItem> list = new ArrayList<>();
     for (int i = 0; i < this.history.size(); i++) {
       LoanItem item = this.history.get(i);
       ListItem listitem = new ListItem(item.getReference(), item.getLoanProduct().getPrincipal().toString());
       list.add(listitem);
     } 
     return list;
   }
 }

