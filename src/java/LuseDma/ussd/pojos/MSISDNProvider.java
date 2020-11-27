 package LuseDma.ussd.pojos;

 public class MSISDNProvider
 {
   private int id;
   private String name;
   
   public MSISDNProvider() { this(0, null); }
   
   public MSISDNProvider(int id, String name) {
     this.id = id;
     this.name = name;
   }
   public int getId() { return this.id; }
   public String getName() { return this.name; }
   public void setId(int s) { this.id = s; }
   public void setName(String s) { this.name = s; }
 }
