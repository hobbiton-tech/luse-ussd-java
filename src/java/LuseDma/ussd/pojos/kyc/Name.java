 package LuseDma.ussd.pojos.kyc;

 public class Name
 {
   private String first;
   private String last;
   
   public Name() { this("John", "Doe"); }
   
   public Name(String first, String last) {
     this.first = first;
     this.last = last;
   }
   public String getFirst() { return this.first; }
   public String getLast() { return this.last; }
   public String getFullname() { return this.first + " " + this.last; }
   public void setFirst(String s) { this.first = s; }
   public void setLast(String s) { this.last = s; }
 }
