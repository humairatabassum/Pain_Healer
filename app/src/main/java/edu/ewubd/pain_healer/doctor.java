package edu.ewubd.pain_healer;

public class doctor {
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String Nid;
    private String title;
    private String gender;
    private String department;
    private String register;
    private String role;

    public doctor() {
    }

    public doctor(String name, String email, String phone, String dob, String nid, String title, String gender, String department, String register, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.Nid = nid;
        this.title = title;
        this.gender = gender;
        this.department = department;
        this.register = register;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
