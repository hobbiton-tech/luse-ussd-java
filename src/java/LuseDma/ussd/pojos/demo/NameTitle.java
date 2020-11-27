 package LuseDma.ussd.pojos.demo;
 

 public enum NameTitle
 {
   Mr, Mrs, Miss, Ms, Dr, Unknown;
 
   
   public String toString() {
     String s = super.toString();
     return s.substring(0, 1) + s.substring(1).toLowerCase();
   }
 }
