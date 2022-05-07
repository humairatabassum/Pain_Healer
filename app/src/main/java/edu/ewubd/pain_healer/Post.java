package edu.ewubd.pain_healer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
//private Button up_pic;
//private static final int pic_img=1;

public class Post {


    public String title;
    public String details;
    public String duration;
    public String age;
    public String doctor;
    public String disease;
    public String department;

    public Post(){
    }

     public Post(String title, String details, String duration, String age, String doctor, String disease, String department) {
        this.title = title;
        this.details = details;
        this.duration = duration;
        this.age = age;
        this.doctor = doctor;
        this.disease = disease;
        this.department = department;
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

//          up_pic = () findViewById(R.id.tv3);
//            up_pic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent galarry = new Intent();
//                    galarry.setType("image/*");
//                    galarry.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(galarry, pic_img);
//                }
//            });

}
