package edu.ewubd.pain_healer;

public class Report {

    private String postUid;
    private String patientUid;
    private String med1;
    private String med2;
    private String quan1;
    private String quan2;
    private String suggestions;
    private String test;
    private String medTime1;
    private String medTime2;
    private String timePeriod1;
    private String timePeriod2;
    private String date;

    public Report() {
    }

    public Report(String postUid, String patientUid, String med1, String med2, String quan1, String quan2, String suggestions, String test, String medTime1, String medTime2 , String timePeriod1, String timePeriod2, String date) {
        this.postUid = postUid;
        this.patientUid = patientUid;
        this.med1 = med1;
        this.med2 = med2;
        this.quan1 = quan1;
        this.quan2 = quan2;
        this.suggestions = suggestions;
        this.test = test;
        this.medTime1 = medTime1;
        this.medTime2 = medTime2;
        this.timePeriod1 = timePeriod1;
        this.timePeriod2 = timePeriod2;
        this.date = date;
    }

    public String getPostUid() {
        return postUid;
    }

    public void setPostUid(String docUid) {
        this.postUid = docUid;
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }

    public String getMed1() {
        return med1;
    }

    public void setMed1(String med1) {
        this.med1 = med1;
    }

    public String getMed2() {
        return med2;
    }

    public void setMed2(String med2) {
        this.med2 = med2;
    }

    public String getQuan1() {
        return quan1;
    }

    public void setQuan1(String quan1) {
        this.quan1 = quan1;
    }

    public String getQuan2() {
        return quan2;
    }

    public void setQuan2(String quan2) {
        this.quan2 = quan2;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getMedTime1() {
        return medTime1;
    }

    public void setMedTime1(String medTime1) {
        this.medTime1 = medTime1;
    }

    public String getMedTime2() {
        return medTime2;
    }

    public void setMedTime2(String medTime2) {
        this.medTime2 = medTime2;
    }

    public String getTimePeriod1() {
        return timePeriod1;
    }

    public void setTimePeriod1(String timePeriod1) {
        this.timePeriod1 = timePeriod1;
    }

    public String getTimePeriod2() {
        return timePeriod2;
    }

    public void setTimePeriod2(String timePeriod2) {
        this.timePeriod2 = timePeriod2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
