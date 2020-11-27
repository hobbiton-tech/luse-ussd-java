 package LuseDma.ussd.pojos.kyc;

 public enum Gender
 {
   MALE, Male, FEMALE, Female, UNKNOWN, Unknown;
   
   public String toString() {
     String s = super.toString();
     return s.substring(0, 1) + s.substring(1).toLowerCase();
   }
 }

