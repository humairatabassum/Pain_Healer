package edu.ewubd.pain_healer;

public class User {
    private String role;
    public String username;
    public String email;
    public String phoneNumber;
    public String gender;


    public User(){
    }
    public User (String username,String email,String phoneNumber,String gender, String role){
        this.username=username;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.gender=gender;
        this.role=role;

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

    public String getGender() { return gender; }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
