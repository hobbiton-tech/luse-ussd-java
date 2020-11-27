package LuseDma.ussd.pojos.demo;
import LuseDma.ussd.pojos.ListItem;

import java.util.Date;
 
 public class LoanPayment extends ListItem
 {
   private String reference;
   private String channel;
   private Double amount;
   private Double outstanding;
   private Double balance;
   private Date payment_date;
   public LoanPayment() { }
   
   public String getReference() { return this.reference; }
   public String getChannel() { return this.channel; }
   public Double getAmount() { return this.amount; }
   public Double getOutstanding() { return this.outstanding; }
   public Double getBalance() { return this.balance; }
   public Date getPaymentDate() { return this.payment_date; }
   
   public void setReference(String v) { this.reference = v; }
   public void setChannel(String v) { this.reference = v; }
   public void setAmount(Double v) { this.amount = v; }   
   public void setOutstanding(Double v) { this.outstanding = v; }
   public void setBalance(Double v) { this.balance = v; }
   public void setPaymentDate(Date v) { this.payment_date = v; }
  
 }
