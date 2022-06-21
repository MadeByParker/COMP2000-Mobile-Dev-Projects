package com.example.students;

public class StudentUser {

    //user object
    private String sStudentId;
    protected String sPassword;
    private String sEmailAddress;

    public StudentUser(String StudentId, String Password, String EmailAddress) {
        sStudentId = StudentId;
        sEmailAddress = EmailAddress;
        sPassword = Password;
    }

    //student serialise and deserialize functions
    public String getStudentId() {
        return sStudentId;
    }

    public void setStudentId(String StudentId) {
        sStudentId = StudentId;
    }

    public String getEmailAddress(){
        return sEmailAddress;
    }

    public void setEmailAddress(String emailAddress){
        sEmailAddress = emailAddress;
    }

    public String getPassword() {
        return sPassword;
    }

    public void setPassword(String password){
        sPassword = password;
    }

}
