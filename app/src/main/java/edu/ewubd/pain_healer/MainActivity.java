package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnSignupPage,  btnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(MainActivity.this, HomePage.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        btnSignupPage = findViewById(R.id.btnSignUp);
        btnLoginPage = findViewById(R.id.logIN);

        btnSignupPage.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PatientRegistration.class);
            startActivity(i);
            System.out.println("signup Button");

        });

        btnLoginPage.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, SignInPage.class);
            startActivity(i);

        });

    }
}
