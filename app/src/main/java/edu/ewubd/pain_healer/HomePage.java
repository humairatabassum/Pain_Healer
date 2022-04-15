package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity {

    TextView ivPic1, ivPic2;
    Button btnLogout;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ivPic1 = findViewById(R.id.post);
        ivPic2 = findViewById(R.id.profile);
        btnLogout = findViewById(R.id.btnLogout);

        ivPic1.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, PostActivity.class);
            startActivity(i);
        });

        ivPic2.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, ProfileActivity.class);
            startActivity(i);
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomePage.this, SignInPage.class);
                startActivity(i);

            }
        });
    }
}