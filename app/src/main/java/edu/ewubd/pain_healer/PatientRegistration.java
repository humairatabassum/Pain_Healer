package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;

    Button btnSignupPatient, btnLoginPage, btnDoctorSignup;

    private static final String defaultURL = "https://firebasestorage.googleapis.com/v0/b/pain-healer.appspot.com/o/default-dp.png?alt=media&token=89b59df3-38e0-489f-8523-b89204436481";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        mAuth = FirebaseAuth.getInstance();

        btnDoctorSignup = findViewById(R.id.DoctorButton);
        btnDoctorSignup.setOnClickListener(view -> {
            Intent intent = new Intent(PatientRegistration.this, doctorRegistration.class);
            startActivity(intent);
        });

        btnSignupPatient = findViewById(R.id.SignUpBtn);
        btnSignupPatient.setOnClickListener(view -> registerUser());

        btnLoginPage = findViewById(R.id.LogInbtn);

        btnLoginPage.setOnClickListener(view -> {
            Intent i = new Intent(PatientRegistration.this, SignInPage.class);
            startActivity(i);
        });
    }

    private void registerUser() {
        EditText etUserName = findViewById(R.id.etUserName);
        Spinner spUserGender = findViewById(R.id.spUserGender);
        EditText etEmail = findViewById(R.id.SignupEmail);
        EditText etPhone = findViewById(R.id.SignupPhone);
        EditText etPass = findViewById(R.id.SignupPass);

        String name = etUserName.getText().toString();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhone.getText().toString();
        String password = etPass.getText().toString().trim();
        String gender = spUserGender.getSelectedItem().toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserGender.setAdapter(adapter);
        spUserGender.setOnItemSelectedListener(this);

        if (phoneNumber.length() < 11 || phoneNumber.length() > 11) {
            Toast.makeText(this, "Phone number isn't valid.", Toast.LENGTH_LONG).show();
            return;
        }

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(mAuth.getCurrentUser().getUid(), name, email, phoneNumber, "", "", "", gender, "", "", "Patient", defaultURL);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(PatientRegistration.this, "Account registration Successfully", Toast.LENGTH_SHORT).show();
                                    showSingupPatient();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                //If email already registered.

                                Toast.makeText(PatientRegistration.this, "Email already registered.", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //If email are in incorrect format

                                Toast.makeText(PatientRegistration.this, "Email format incorrect.", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {

                                Toast.makeText(PatientRegistration.this, "Password is too weak\n Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }

    public void showSingupPatient() {
        finish();
        Intent intent = new Intent(PatientRegistration.this, SignInPage.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String item = adapterView.getItemAtPosition(0).toString();
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }
}
