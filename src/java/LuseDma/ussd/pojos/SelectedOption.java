 package LuseDma.ussd.pojos;
 
 import java.util.Map;

 public class SelectedOption
 {
   private String id;
   private String name;
   private Integer index;
   private Map map;
   
   public SelectedOption() { this("none", "none"); }
   
   public SelectedOption(String id, String name) {
     this.id = id;
     this.name = name;
   }
   
   public SelectedOption(Integer index, String name) {
     this.index = index;
     this.name = name;
   }
 
   public SelectedOption(Integer index,Map map) { this.index = index;this.map = map; }
   public SelectedOption(Map map) { this.map = map; }
   
   public String getId() { return this.id; }
   public String getName() { return this.name; }
   public Integer getIndex() { return this.index; }
   public Map getItem() { return this.map; }
   public void setId(String s) { this.id = s; }
   public void setName(String s) { this.name = s; }
   public void setIndex(Integer s) { this.index = s; }
   public void setItem(Map s) { this.map = s; }
 }
