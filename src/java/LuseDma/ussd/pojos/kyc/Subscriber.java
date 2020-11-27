 package LuseDma.ussd.pojos.kyc;
 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 @JsonIgnoreProperties(ignoreUnknown = true)
 
 public class Subscriber
 {
    private String msisdn;
    private String idnumber;
    private PersonalDetails personal_details;
    private IncomeDetails income_details;
    private ContactDetails contact_details;
    private NextOfKin next_of_kin;
    private String status;

    public String getMsisdn () { return msisdn;}
    public void setMsisdn (String msisdn){this.msisdn = msisdn;}
    
    public String getIdnumber (){ return idnumber;}
    public void setIdnumber (String idnumber){ this.idnumber = idnumber;}
    
    public PersonalDetails getPersonalDetails () {  return personal_details; }
    public void setPersonalDetails (PersonalDetails personal_details)
    {
        this.personal_details = personal_details;
    }
    public ContactDetails getContactDetails (){ return contact_details; }
    public void setContactDetails (ContactDetails contact_details)
    {
        this.contact_details = contact_details;
    }
    public IncomeDetails getIncomeDetails (){ return income_details; }
    public void setIncomeDetails (IncomeDetails income_details)
    {
        this.income_details = income_details;
    }
    public NextOfKin getNextOfKin () { return next_of_kin;}
    public void setNextOfKin (NextOfKin next_of_kin)
    {
        this.next_of_kin = next_of_kin;
    }

    public String getStatus () { return status;}
    public void setStatus (String status){ this.status = status;}
}
