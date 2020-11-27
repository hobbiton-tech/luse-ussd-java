 package LuseDma.ussd.pojos;
 
 import java.util.Date;

 public class Account
 {
   private String reference;
   private String loan_id;
   private String loan_name;
   private Double principal = Double.valueOf(0.0D);
   private Double interest = Double.valueOf(0.0D);
   private Double repayment = Double.valueOf(0.0D);
   private Double outstanding = Double.valueOf(0.0D);
   private Double repaid = Double.valueOf(0.0D);
   private Boolean owing = false;
   private Date date_borrowed;
   private Date date_due;
   public Account() { }
   
   public String getReference() { return this.reference; }
   public String getLoanId() { return this.loan_id; }
   public String getLoanName() { return this.loan_name; }
   public Double getPrincipal() { return this.principal; }
   public Double getInterest() { return this.interest; }
   public Double getRepayment() { return this.repayment; }
   public Double getOutstanding() { return this.outstanding; }
   public Double getRepaid() { return this.repaid; }
   public Boolean getOwing() { return this.owing; }
   public Date getDateBorrowed() { return this.date_borrowed; }
   public Date getDateDue() { return this.date_due; }
   
   public void setReference(String v) { this.reference = v; }
   public void setLoanId(String v) { this.loan_id = v; }
   public void setLoanName(String v) { this.loan_name = v; }
   public void setPrincipal(Double v) { this.principal = v; }  
   public void setInterest(Double v) { this.interest = v; } 
   public void setRepayment(Double v) { this.repayment = v; }
   public void setOutstanding(Double v) { this.outstanding = v; }
   public void setRepaid(Double v) { this.repaid = v; }
   public void setOwing(Boolean v) { this.owing = v; }
   public void setDateBorrowed(Date v) { this.date_borrowed = v; }
   public void setDateDue(Date v) { this.date_due = v; }
 }

