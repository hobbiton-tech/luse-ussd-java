 package LuseDma.ussd.pojos.demo;
 

 public enum MaritalStatus
 {
   Married, Widowed, Separated, Divorced, Single, Unknown;
   public String toString() {
     String s = super.toString();
     return s.substring(0, 1) + s.substring(1).toLowerCase();
   }
 }

