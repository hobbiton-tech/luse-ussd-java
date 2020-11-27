 package LuseDma.ussd.pojos;
 

 public class Attribute
 {
   private Integer id = Integer.valueOf(0); public Attribute() {}
   private String desc = null;
 
   
   public Attribute(int id, String desc) {
     this.id = Integer.valueOf(id);
     this.desc = desc;
   }
   public Integer getId() { return this.id; }
   public String getDesc() { return this.desc; }
   public void setId(Integer v) { this.id = v; }
   public void setDesc(String v) { this.desc = v; }
 }

