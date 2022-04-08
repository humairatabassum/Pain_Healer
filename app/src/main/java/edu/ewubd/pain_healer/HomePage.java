package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class HomePage extends AppCompatActivity {

    ImageView ivPic1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ivPic1=findViewById(R.id.pic1);
        ivPic1.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this,PostActivity.class);
            startActivity(i);
        });
    }
}