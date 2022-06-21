package com.example.farmbnb_cw1;

public class User {

    //user object
    private String lUsername;
    protected String lPassword;
    private String lEmailAddress;

    public User(String Username, String Password, String EmailAddress) {
        lUsername = Username;
        lEmailAddress = EmailAddress;
        lPassword = Password;
    }

    public String getUsername() {
        return lUsername;
    }

    public void setUsername(String username) {
        lUsername = username;
    }

    public String getEmailAddress(){
        return lEmailAddress;
    }

    public void setEmailAddress(String emailAddress){
        lEmailAddress = emailAddress;
    }

    public String getPassword() {
        return lPassword;
    }

    public void setPassword(String password){
        lPassword = password;
    }

}
