package edu.ewubd.pain_healer;

public class Post {

    public String uid;
    public String patientUid;
    public String title;
    public String details;
    public String duration;
    public String age;
    public String doctor;
    public String doctorUid;
    public String disease;
    public String department;

    public Post() {
    }

    public Post(String patientUid, String uid, String title, String details, String duration, String age, String doctor, String doctorUid, String disease, String department) {
        this.patientUid = patientUid;
        this.uid = uid;
        this.title = title;
        this.details = details;
        this.duration = duration;
        this.age = age;
        this.doctor = doctor;
        this.doctorUid = doctorUid;
        this.disease = disease;
        this.department = department;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctorUid() {
        return doctorUid;
    }

    public void setDoctorUid(String doctorUid) {
        this.doctorUid = doctorUid;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }
}
