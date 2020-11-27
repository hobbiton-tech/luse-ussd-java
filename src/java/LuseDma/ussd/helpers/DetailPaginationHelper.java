 package LuseDma.ussd.helpers;
 
 import java.io.UnsupportedEncodingException;
 import java.util.ArrayList;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.ListItem;

 public final class DetailPaginationHelper
 {
   private int listPageActionHandle;
   private String listselection;
   private String newLine = String.format("%n", new Object[0]);
   private ArrayList<ListItem> listitems;
   private USSDSession ussdsession;
   private String header;
   private String trailerEstimate;
   private String bodyAndTrailer;
   private String fullUssdMessage;
   private String emptyMsg;
   private String addToList;
   private final int maxGsmUssdLength = 182;
   private int accurateBodyLength;
   
   public DetailPaginationHelper(USSDSession session, ArrayList<ListItem> listitems, String header, String addtolist, String emptymsg) {
     this.ussdsession = session;
     this.listitems = listitems;
     this.header = header;
     this.addToList = addtolist;
     this.emptyMsg = emptymsg;
     this.trailerEstimate = "";
     this.bodyAndTrailer = "";
     this.fullUssdMessage = "";
     this.accurateBodyLength = getAccuratebodylength();
     this.listPageActionHandle = getListPageAction();
   }
   private int lastpagecount;
   public int getAccuratebodylength() {
     this.trailerEstimate += AppConfigHelper.getPriorPageText() + this.newLine;
     this.trailerEstimate += AppConfigHelper.getNextPageText() + this.newLine;
     if (!this.addToList.equals("")) {
       this.trailerEstimate += this.addToList + this.newLine;
     }
     this.trailerEstimate += AppConfigHelper.getGoBackText();
     
     return this.accurateBodyLength = maxGsmUssdLength - (getGSMUSSDLength(this.header) + getGSMUSSDLength(this.trailerEstimate));
   }
   private int getGSMUSSDLength(String str) {
     int bytes = 0;
     try {
       bytes = (str.getBytes("ASCII")).length;
     } catch (UnsupportedEncodingException ex) {
       Logger.getLogger(DetailPaginationHelper.class.getName()).log(Level.SEVERE, null, ex);
     } 
     return bytes * 8 / 7;
   }
   private int getLastPageCount() {
     int count = 1, j = 0;
     for (int i = 1; i < this.listitems.size() - this.ussdsession.getOffSet(); ) {
       j = this.listitems.size() - i;
       if (j >= this.ussdsession.getOffSet() + 1) {
         count++;
         i++;
       } 
     } 
     return count;
   }
   private int getPriorPageOffset() {
     String body = "";
     int offset = 0;
     int perpage = 0;
     int position = this.ussdsession.getOffSet();
     for (int i = this.ussdsession.getOffSet() - 1; i >= 0; i--) {
       ListItem listitem = this.listitems.get(i);
       String itemid = listitem.getItemID();
       String itemname = listitem.getItemName();
       String menuitem = position + ". " + itemname + this.newLine;
       if (getGSMUSSDLength(body + menuitem) <= this.accurateBodyLength) {
            body = body + menuitem;
            perpage++;
            position--;
            if (i == 0){
              offset = i;
              this.ussdsession.setOffSet(offset);
            }
       } else {
            offset = i + 1;
            this.ussdsession.setOffSet(offset);
            break;
       } 
     } 
     return offset;
   }
   public String getListPage() {
     String body = "";
     String trailer = "";
     if (this.listitems == null || this.listitems.isEmpty()) {
       body = this.emptyMsg + this.newLine;
       if (!this.addToList.equals("")) {
         trailer = trailer + this.addToList + this.newLine;
       }
       trailer = trailer + AppConfigHelper.getGoBackText();
     } else {
       int perpage = 0;
       int position = this.ussdsession.getOffSet() + 1;
       for (int i = this.ussdsession.getOffSet(); i < this.listitems.size(); i++) {
         
         ListItem listitem = this.listitems.get(i);
         String itemid = listitem.getItemID();
         String itemname = listitem.getItemName();
         String menuitem = itemid + ":" + itemname + this.newLine;
         if (getGSMUSSDLength(body + menuitem) <= this.accurateBodyLength) {
           body = body + menuitem;
           perpage++;
           position++;
         } else {
           this.ussdsession.setPriorPerPage(this.ussdsession.getPerPage());
           this.ussdsession.setPerPage(perpage);
           
           if (this.ussdsession.getOffSet() == 0) {
                trailer = trailer + AppConfigHelper.getNextPageText() + this.newLine;
               if (!this.addToList.equals("")) {
                  trailer = trailer + this.addToList + this.newLine;
               }
                trailer = trailer + AppConfigHelper.getGoBackText();
               this.ussdsession.setIsLastPage(0); break;
           } 
           trailer = trailer + AppConfigHelper.getPriorPageText() + this.newLine;
           trailer = trailer + AppConfigHelper.getNextPageText() + this.newLine;
           if (!this.addToList.equals("")) {
             trailer = trailer + this.addToList + this.newLine;
           }
           trailer = trailer + AppConfigHelper.getGoBackText();
           this.ussdsession.setIsLastPage(0);
           break;
         } 
         
         if (i == this.listitems.size() - 1) {
           this.ussdsession.setPriorPerPage(this.ussdsession.getPerPage());
           this.ussdsession.setPerPage(perpage);
           if (this.ussdsession.getOffSet() == 0) {
             
             this.ussdsession.setIsLastPage(1);
             if (!this.addToList.equals("")) {
               trailer = trailer + this.addToList + this.newLine;
             }
             trailer = trailer + AppConfigHelper.getGoBackText(); break;
           } 
           AppConfigHelper.getInstance(); trailer = trailer + AppConfigHelper.getPriorPageText() + this.newLine;
           if (!this.addToList.equals("")) {
             trailer = trailer + this.addToList + this.newLine;
           }
           AppConfigHelper.getInstance(); trailer = trailer + AppConfigHelper.getGoBackText();
           this.ussdsession.setIsLastPage(1);
           
           break;
         } 
       } 
     } 
     return this.header + body + trailer;
   }
   public int getListPageActionHandle() { return this.listPageActionHandle; }
   private int getListPageAction() {
     int handle = 4;
     this.listselection = this.ussdsession.getUserInput();
     
     AppConfigHelper.getInstance(); 
     if (this.listselection.equalsIgnoreCase(AppConfigHelper.getGoBackCharacter())){ 
       handle = 5; 
     }
     else { 
        AppConfigHelper.getInstance(); 
        if (this.listselection.equalsIgnoreCase(AppConfigHelper.getAddToListCharacter())) {
            handle = 4;
        } else {
         AppConfigHelper.getInstance(); 
         if (this.listselection.equalsIgnoreCase(AppConfigHelper.getNextPageCharacter())){ 
            if (this.ussdsession.getIsLastPage() == 1) {
                handle = 4;
            } else {
              handle = 3;
              this.ussdsession.setCurrentPage(this.ussdsession.getCurrentPage() + 1);
              int offset = this.ussdsession.getOffSet() + this.ussdsession.getPerPage();
              this.ussdsession.setOffSet(offset);
            }  
        } else { 
             AppConfigHelper.getInstance(); 
            if (this.listselection.equalsIgnoreCase(AppConfigHelper.getPriorPageCharacter())) {
                if (this.ussdsession.getOffSet() == 0) {
                  handle = 4;
                } else {
                  handle = 2;
                  this.ussdsession.setCurrentPage(this.ussdsession.getCurrentPage() - 1);
                  getPriorPageOffset();
                } 
            } else if (this.ussdsession.isValidInteger().booleanValue()) {
                int NextLimit, PriorLimit = this.ussdsession.getOffSet();
                if (this.ussdsession.getOffSet() == 0) {
                  NextLimit = this.ussdsession.getOffSet() + this.ussdsession.getPerPage();
                } else if (this.ussdsession.getIsLastPage() == 1) {
                  NextLimit = this.ussdsession.getOffSet() + this.ussdsession.getPriorPerPage() + 1;
                } else {
                  NextLimit = this.ussdsession.getOffSet() + this.ussdsession.getPriorPerPage();
                } 
                if (Integer.parseInt(this.listselection) > PriorLimit && Integer.parseInt(this.listselection) <= NextLimit) {
                  handle = 1;
                } else {
                  handle = 4;
                } 
            } else {
                handle = 4;
            }  
         }
       }  
    }
    return handle;
  }
}

