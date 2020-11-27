 package LuseDma.ussd.pojos.demo;
 
 import LuseDma.ussd.pojos.ListItem;

 import java.util.ArrayList;
import java.util.Date;
 public class LoanItem extends ListItem {
   private String reference = "";
   private String msisdn = "";
   private LoanProductsItem product;
   private Double outstanding = 0D;
   private Double repaid = 0D;
   private Date date_borrowed;
   private Date date_due;
   private Date date_paid;
   private Integer status = 0;
   private ArrayList<LoanPayment> payments = new ArrayList<>();
   public LoanItem() { }
   
   public LoanItem(String itemid, String itemname, String reference) {
     super(itemid, itemname);
     this.reference = reference;
   }
   
   public String getReference() { return this.reference; }
   public String getMsisdn() { return this.msisdn; }
   public LoanProductsItem getLoanProduct() { return this.product; }
   public Double getOutstanding() { return this.outstanding; }
   public Double getRepaid() { return this.repaid; }
   public Date getDateBorrowed() { return this.date_borrowed; }
   public Date getDateDue() { return this.date_due; }
   public Date getDatePaid() { return this.date_paid; }
   public ArrayList<LoanPayment> getLoanPayments() { return this.payments; }
   public Integer getStatus() { return this.status; }
      
   public void setReference(String v) { this.reference = v; }
   public void setMsisdn(String v) { this.msisdn = v; }
   public void setLoanProduct(LoanProductsItem v) { this.product = v; }   
   public void setOutstanding(Double v) { this.outstanding = v; }
   public void setRepaid(Double v) { this.repaid = v; }
   public void setDateBorrowed(Date v) { this.date_borrowed = v; }
   public void setDateDue(Date v) { this.date_due = v; }
   public void setDatePaid(Date v) { this.date_paid = v; }
   public void setLoanPayments(ArrayList<LoanPayment> v) { this.payments = v; }
   public void setStatus(Integer  v) { this.status = v; }
 }
