 package LuseDma.ussd.handlers.demo;
 
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.demoViews.GenericViews;
import LuseDma.ussd.helpers.USSDSession;


 public class TermsAndConditionsHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private GenericViews view = new GenericViews();
   public TermsAndConditionsHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
   }
   public USSDResponse runSession() {
        this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
        return this.ussdsession.buildUSSDResponse(this.view.getTermAndConditionsView(), 2);
   } 
 
 }
