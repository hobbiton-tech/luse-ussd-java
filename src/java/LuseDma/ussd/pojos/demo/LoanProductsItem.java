 package LuseDma.ussd.pojos.demo;

 public class LoanProductsItem
 {
   private String id;
   private String name;
   private Double principal;
   private Double interest;
   private Double repayment;
   private Integer duration;
   public LoanProductsItem() { }
   
   public LoanProductsItem(String id, String name) {
     this.id = id;
     this.name = name;
   }
  
   public String getId() { return this.id; }
   public String getName() { return this.name; }
   public Double getPrincipal() { return this.principal; }
   public Double getInterest() { return this.interest; }
   public Double getRepayment() { return this.repayment; }
   public Integer getDuration() { return this.duration; }
   
   public void setId(String s) { this.id = s; }
   public void setName(String s) { this.name = s; }
   public void setPrincipal(Double s) { this.principal = s; }
   public void setInterest(Double s) { this.interest = s; }
   public void setRepayment(Double s) { this.repayment = s; }
   public void setDuration(Integer s) { this.duration = s; }
   
 }
