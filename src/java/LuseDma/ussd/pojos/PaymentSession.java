 package LuseDma.ussd.pojos;


 public class PaymentSession
 {
   private String msisdn = null; public PaymentSession() {}
   private String value = null;
   
   public PaymentSession(String msisdn, String value) {
     this();
     this.msisdn = "26" + msisdn;
     this.value = value;
   }
   public String getMSISDN() { return this.msisdn; }
   public String getValue() { return this.value; }
   public void setMSISDN(String v) { this.msisdn = "26" + v; }
   public void setValue(String v) { this.value = v; }
 }
