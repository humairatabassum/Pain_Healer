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
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btnSignupPatient, btnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser()!= null){
//            finish();
//            return;
//        }

        btnSignupPatient=findViewById(R.id.SignUpBtn);
        btnSignupPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        btnLoginPage=findViewById(R.id.LogInbtn);

        btnLoginPage.setOnClickListener(view -> {
            Intent i = new Intent(PatientRegistration.this,SignInPage.class);
            startActivity(i);
        });
    }
    private void registerUser(){
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etEmail = findViewById(R.id.SignupEmail);
        EditText etPhone = findViewById(R.id.SignupPhone);
        EditText etPass = findViewById(R.id.SignupPass);

        String firstname= etFirstName.getText().toString();
        String email= etEmail.getText().toString();
        String phoneNumber= etPhone.getText().toString();
        String password= etPass.getText().toString();

        if (firstname.isEmpty()||email.isEmpty()||phoneNumber.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user= new User(firstname, email, phoneNumber);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    showSingupPatient();
                                }
                            });
                        } else {
                            Toast.makeText(PatientRegistration.this,"Authentication failed." ,
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void showSingupPatient(){
        finish();
        Intent intent = new Intent(PatientRegistration.this,SignInPage.class);
        startActivity(intent);
    }

}
