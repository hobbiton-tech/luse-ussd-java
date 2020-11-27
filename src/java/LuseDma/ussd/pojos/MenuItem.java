 package LuseDma.ussd.pojos;

 public class MenuItem
 {
   private String name = null;
   private Class handler = null;
   
   public MenuItem(String name, Class handler) {
     this();
     this.name = name;
     this.handler = handler;
   }
   public MenuItem() {}
   public String getName() { return this.name; }
   public Class getHandler() { return this.handler; }
   
   public void setName(String v) { this.name = v; }
   public void setHandler(Class v) { this.handler = v; }
 }
