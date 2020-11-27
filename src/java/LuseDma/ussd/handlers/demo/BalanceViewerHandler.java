 package LuseDma.ussd.handlers.demo;
 
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.helpers.DetailPaginationHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.SelectedOption;
import LuseDma.ussd.views.demoViews.AccountViews;
 import java.util.ArrayList;


 public class BalanceViewerHandler
 {
   private final int handlersessionlevel = 1;
   private USSDSession ussdsession = null;
   private MobileSession mobilesession = null;
   private ArrayList<ListItem> listitems = null;
   private String header;
   private SelectedOption option;
   private String msisdn;
   private AccountViews view = new AccountViews();
   public BalanceViewerHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
     this.mobilesession = ussdsession.getMobileSession();
     this.msisdn = this.ussdsession.getMSISDN();
   }
   
   public USSDResponse runSession() {
     this.header = this.view.getGenericListHeadersView("account");
     this.listitems = this.view.getBalanceDetailsView(this.mobilesession.getAccount());
     DetailPaginationHelper paginationHelper = null;
     switch (this.ussdsession.getSessionLevel()) {
       case handlersessionlevel:
         this.ussdsession.resetList();
         this.ussdsession.saveUSSDSession(handlersessionlevel+1);
         this.ussdsession.saveSessionMode(1);
         paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
         return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
       case handlersessionlevel+1:
         paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
         switch (paginationHelper.getListPageActionHandle()) {
         
         } 
         this.ussdsession.saveSessionMode(1);
         return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
     } 
     return runSession();
   }
 }
