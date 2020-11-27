package LuseDma.ussd.pojos.luse;

public class TestUser {

    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String gender;
    private String password;

    public TestUser(){

    }

    public TestUser(String fullName, String phone, String gender) {
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
