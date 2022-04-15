package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInPage extends AppCompatActivity {

    Button btnSigninpage, btnSignupPage;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser() != null) {
//        finish();
//        return;
//
//        }
        btnSigninpage = findViewById(R.id.SigninButton);
        btnSignupPage = findViewById(R.id.SignUp);


        btnSigninpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        btnSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInPage.this, PatientRegistration.class);
                startActivity(intent);
            }
        });
    }
    private void authenticateUser(){
        EditText etEmail = findViewById(R.id.LoginEmail);
        EditText etPass = findViewById(R.id.LoginPW);

        String email= etEmail.getText().toString();
        String password= etPass.getText().toString();

        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(SignInPage.this,HomePage.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignInPage.this,"Authentication failed." ,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}