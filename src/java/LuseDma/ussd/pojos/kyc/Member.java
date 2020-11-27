 package LuseDma.ussd.pojos.kyc;
 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
 
 @JsonIgnoreProperties(ignoreUnknown = true)
 
 public class Member
 {
    private String msisdn;
    private String ssn;
    private String idnumber;
    private String sector;
    private String title;
    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String province;
    private String address;
    private Date date = new Date();
    private Integer status = 1;

    public String getMsisdn () { return msisdn;}
    public void setMsisdn (String msisdn){this.msisdn = msisdn;}
    public String getSSN () { return ssn;}
    public void setSSN (String ssn){this.ssn = ssn;}
    public String getIdnumber (){ return idnumber;}
    public void setIdnumber (String idnumber){ this.idnumber = idnumber;}
    public String getSector (){ return sector;}
    public void setSector (String sector){ this.sector = sector;}
    
    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }
    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }
    public String getMiddlename ()
    {
        return middlename;
    }

    public void setMiddlename (String middlename)
    {
        this.middlename = middlename;
    }
    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }
    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getProvince ()
    {
        return province;
    }

    public void setProvince (String province)
    {
        this.province = province;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }
    public Date getDate() { return date;}
    public void setDate (Date status){ this.date = date;}
    public Integer getStatus () { return status;}
    public void setStatus (Integer status){ this.status = status;}
}
