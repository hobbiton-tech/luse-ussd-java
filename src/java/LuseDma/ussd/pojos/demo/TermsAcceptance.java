 package LuseDma.ussd.pojos.demo;
 
 import java.util.Date;

 public class TermsAcceptance
 {
   private Integer option = Integer.valueOf(0);
   private String acceptance = "No";
   private Date dateaccepted = new Date();
   
   public TermsAcceptance(Integer option, String acceptance) {
     this();
     this.option = option;
     this.acceptance = acceptance;
     this.dateaccepted = new Date();
   }
   public TermsAcceptance() {}
   public Integer getOption() { return this.option; }
   public String getAcceptance() { return this.acceptance; }
   public Date getDateAccepted() { return this.dateaccepted; }
   
   public void setOption(Integer v) { this.option = v; }
   public void setAcceptance(String v) { this.acceptance = v; }
   public void setDateAccepted(Date v) { this.dateaccepted = v; }
 }
