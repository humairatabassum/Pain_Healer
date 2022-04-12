package edu.ewubd.pain_healer;

public class User {
    public String username;
    public String email;
    public String phoneNumber;

    public User(){
    }
    public User (String firstname,String email,String phoneNumber ){
        this.username=firstname;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
