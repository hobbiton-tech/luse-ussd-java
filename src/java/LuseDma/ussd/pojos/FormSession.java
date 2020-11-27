 package LuseDma.ussd.pojos;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;


 public class FormSession
 {
   private HashMap formdata = new HashMap<>();
   private List<Map<String, Object>> formlist = new ArrayList<>();
 
   
   public HashMap getFormData() { return this.formdata; }
   public HashMap getForm(String name) { return (HashMap)this.formdata.getOrDefault(name, new HashMap<>()); }
   public void clearFormData(String name) { 
       this.formdata.put(name, new HashMap<>()); 
   }
   public List getFormList() { return this.formlist; }
   
   public void setFormData(HashMap v) { this.formdata = v; }
   public void setFormList(ArrayList<Map<String, Object>> v) { this.formlist = v; }
 }
