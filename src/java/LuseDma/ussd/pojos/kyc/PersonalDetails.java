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
public class PersonalDetails
{
    private String firstname;

    private String gender;

    private String nationality;

    private String date_of_birth;

    private String middlename;

    private String id_type;

    private String title;

    private String lastname;

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

    public String getNationality ()
    {
        return nationality;
    }

    public void setNationality (String nationality)
    {
        this.nationality = nationality;
    }

    public String getDateOfBirth ()
    {
        return date_of_birth;
    }

    public void setDateOfBirth (String date_of_birth)
    {
        this.date_of_birth = date_of_birth;
    }
    public String getIdType ()
    {
        return id_type;
    }

    public void setIdType (String id_type)
    {
        this.id_type = id_type;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }
}
			
