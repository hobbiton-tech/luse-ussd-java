 package LuseDma.ussd.pojos;
 
 import java.util.ArrayList;

 public class SelectionSession
 {
   private Boolean status = Boolean.valueOf(false);
   private Integer index = Integer.valueOf(0);
   private Integer total_items = Integer.valueOf(0);
   private ArrayList<String> selection = new ArrayList<>();
 
   
   public Boolean getStatus() { return this.status; }
   public Integer getIndex() { return this.index; }
   public Integer getTotalItems() { return this.total_items; }
   public ArrayList<String> getSelection() { return this.selection; }
   public String getSelectionString() {
     String str = "";
     for (int i = 0; i < this.selection.size(); i++) {
       str = str + (String)this.selection.get(i);
     }
     return str;
   }
   
   public void setStatus(Boolean v) { this.status = v; }
   public void setIndex(Integer v) { this.index = v; }
   public void setTotalItems(Integer v) { this.total_items = v; }
   public void setSelection(ArrayList<String> v) { this.selection = v; }
   public void setSelections(String option, int position) {
     if (this.selection.size() > position) {
       this.selection.set(position, option);
     } else {
       this.selection.add(option);
     } 
   }
 }

