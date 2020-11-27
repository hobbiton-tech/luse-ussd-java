 package LuseDma.ussd.helpers;
 
 import java.util.ArrayList;

 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.SelectionListItem;
 import LuseDma.ussd.pojos.SelectionSession;
 import LuseDma.ussd.views.demoViews.GenericViews;

 public final class SelectionHelper
 {
   private int listpageactionhandle;
   private String listselection;
   private String newLine = String.format("%n", new Object[0]);
   private ArrayList<SelectionListItem> listitems;
   private USSDSession ussdsession;
   private SelectionSession selectionsession;
   private String header;
   private String trailerestimate;
   private String bodyandtrailer;
   private String fullussdmessage;
   private String emptymsg;
   private String addtolist;
   private final int maxgsmussdlength = 182;
   
   private int accuratebodylength;
   private int lastpagecount;
   private GenericViews view = null;
   public SelectionHelper(USSDSession session, ArrayList<SelectionListItem> listitems, GenericViews view) {
     this.ussdsession = session;
     this.view = view;
     this.listitems = listitems;
     this.header = "";
     this.emptymsg = "Sorry no games found";
     this.trailerestimate = "";
     this.bodyandtrailer = "";
     this.fullussdmessage = "";
     this.listpageactionhandle = getListPageAction();
     this.selectionsession = this.ussdsession.getMobileSession().getSelectionSession();
   }
   public String getOptionPage() {
     String body = "";
     String trailer = "";
     this.selectionsession = this.ussdsession.getMobileSession().getSelectionSession();
     if (this.listitems == null || this.listitems.isEmpty()) {
       body = this.emptymsg + this.newLine;
       AppConfigHelper.getInstance(); trailer = trailer + AppConfigHelper.getGoBackText();
     } else {
       int perpage = 0;
       int position = this.selectionsession.getIndex().intValue();
       
       SelectionListItem listitem = this.listitems.get(position);
       String option = "";
       body = body + option;
       
       if (position == 0) {
         trailer = trailer + "## Go Back";
       } else {
         trailer = trailer + "# Prior fixture" + this.newLine;
         trailer = trailer + "## Start over";
       } 
     } 
     return this.header + body + trailer;
   }
   public int getListPageActionHandle() { return this.listpageactionhandle; }
   private int getListPageAction() {
     int handle = 4;
     this.selectionsession = this.ussdsession.getMobileSession().getSelectionSession();
     this.listselection = this.ussdsession.getUserInput();
     
     AppConfigHelper.getInstance(); if (this.listselection.equalsIgnoreCase(AppConfigHelper.getGoBackCharacter())) {
       
       handle = 5;
       this.ussdsession.resetSelectionOptions();
     } else {
       
       AppConfigHelper.getInstance(); if (this.listselection.equalsIgnoreCase(AppConfigHelper.getPriorPageCharacter())) {
         if (this.selectionsession.getIndex().intValue() == 0) {
           
           handle = 4;
         } else {
           
           handle = 2;
           int idx = this.selectionsession.getIndex().intValue();
           this.selectionsession.setIndex(Integer.valueOf(idx - 1));
         } 
       } else if (isValidSelection(this.listselection)) {
         if (this.ussdsession.getSessionLevel() != 1) {
 
           
           int idx = this.selectionsession.getIndex().intValue();
           this.selectionsession.setIndex(Integer.valueOf(idx + 1));
           this.selectionsession.setSelections(this.listselection.toUpperCase(), idx);
 
 
           
           if (idx == this.listitems.size() - 1) {
             
             handle = 1;
           } else {
             
             handle = 3;
           } 
         } 
       } else {
         
         handle = 4;
       } 
     } 
     this.ussdsession.saveSelectionSession(this.selectionsession);
     return handle;
   }
   public boolean isValidSelection(String v) {
     switch (v) {
       case "1":
         return true;
       case "x":
         return true;
       case "X":
         return true;
       case "2":
         return true;
     } 
     return false;
   }
 }

