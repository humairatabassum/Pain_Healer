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
import com.google.firebase.database.FirebaseDatabase;


public class doctorRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    String role = "pending";
    Button register, logIn;

    private static final String defaultURL = "https://firebasestorage.googleapis.com/v0/b/pain-healer.appspot.com/o/default-dp.png?alt=media&token=89b59df3-38e0-489f-8523-b89204436481";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.btnSignUp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerDoctor();
            }
        });

        logIn = findViewById(R.id.btnLogIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctorRegistration.this, SignInPage.class);
                startActivity(intent);
            }
        });

    }

    private void registerDoctor() {
        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPass);
        EditText etnId = findViewById(R.id.Nid);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etDoB = findViewById(R.id.date);
        EditText etRegister = findViewById(R.id.registrationNum);
        Spinner spinnerTitle = findViewById(R.id.spTitle);
        Spinner spinnerGender = findViewById(R.id.GenderSpinner);
        Spinner spinnerDepartment = findViewById(R.id.doctorType);

        String name = etName.getText().toString();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String nid = etnId.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDoB.getText().toString().trim();
        String register = etRegister.getText().toString().trim();
        String title = spinnerTitle.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        String department = spinnerDepartment.getSelectedItem().toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.department, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter2);
        spinnerDepartment.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.title, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTitle.setAdapter(adapter3);
        spinnerTitle.setOnItemSelectedListener(this);


        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || nid.isEmpty() || dob.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(mAuth.getCurrentUser().getUid(), name, email, phone, dob, nid, title, gender, department, register, "Pending", defaultURL);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(doctorRegistration.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(doctorRegistration.this, "Your account is pending for admin permission", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                //If email already registered.

                                Toast.makeText(doctorRegistration.this, "Email already registered.", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //If email are in incorrect format

                                Toast.makeText(doctorRegistration.this, "Email format incorrect.", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {

                                Toast.makeText(doctorRegistration.this, "Password is too weak\n Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String item = adapterView.getItemAtPosition(0).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }
}