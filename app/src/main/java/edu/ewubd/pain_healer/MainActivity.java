package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSignupPage,  btnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
