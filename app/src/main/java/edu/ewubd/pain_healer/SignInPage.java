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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInPage extends AppCompatActivity {

    Button btnSigninpage, btnSignupPage;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    private void authenticateUser() {
        EditText etEmail = findViewById(R.id.LoginEmail);
        EditText etPass = findViewById(R.id.LoginPW);

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String userId = mAuth.getCurrentUser().getUid();
                        System.out.println("toni User ID: " + userId);
                        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);

                                if (user.getRole().equalsIgnoreCase("Patient") || user.getRole().equalsIgnoreCase("Doctor") || user.getRole().equalsIgnoreCase("Admin")) {
                                    System.out.println("User Role: " + user.getRole());
                                    if (task.isSuccessful()) {
                                        finish();
                                        Intent intent = new Intent(SignInPage.this, HomePage.class);
                                        startActivity(intent);
                                    } else if (!task.isSuccessful()) {
                                        Toast.makeText(SignInPage.this, "Authentication failed.",
                                                Toast.LENGTH_LONG).show();
                                    }

                                } else if (user.getRole().equalsIgnoreCase("Pending")) {
                                    Toast.makeText(SignInPage.this, "Please wait for admin to approve your account.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(SignInPage.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                });
    }
}