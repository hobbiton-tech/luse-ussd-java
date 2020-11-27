/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LuseDma.ussd.pojos.kyc;

/**
 *
 * @author Chisha
 */
public class NextOfKin {
    private String firstname;
    private String lastname;
    private String alternate_number;
    private String mobile_number;
    private String email;
    private String relation;
    
    public String getFirstname ()
    {
        return firstname;
    }
    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }
    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getAlternateNumber ()
    {
        return alternate_number;
    }

    public void setAlternateNumber (String alternate_number)
    {
        this.alternate_number = alternate_number;
    }

    public String getMobileNumber ()
    {
        return mobile_number;
    }

    public void setMobileNumber (String mobile_number)
    {
        this.mobile_number = mobile_number;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }
    public String getRelation ()
    {
        return relation;
    }

    public void setRelation (String relation)
    {
        this.relation = relation;
    }
}
