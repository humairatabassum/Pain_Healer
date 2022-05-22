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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTitle, editDetails, editDuration, editAge;
    private Spinner spDiseases,spGender;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userKey,doctor;
    private String userId;
    private String doctorUid;
    private ArrayList<User> doctorList;
    private String isReport ="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        Intent intent = getIntent();
        doctorUid = intent.getStringExtra("uid");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();
        userKey = mDatabase.push().getKey();


        editTitle = findViewById(R.id.etTitle);
        editDetails = findViewById(R.id.etDetails);
        editDuration = findViewById(R.id.etDuration);
        editAge = findViewById(R.id.etAge);
        //editDoctor = findViewById(R.id.etDoctorName);
        spDiseases = findViewById(R.id.spDisease);
        spGender = findViewById(R.id.spGender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diseases, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDiseases.setAdapter(adapter);
        spDiseases.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter1);
        spGender.setOnItemSelectedListener(this);


        FirebaseDatabase.getInstance().getReference().child("Users").child(doctorUid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        doctor = user.getName();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PostActivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });





        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
                finish();
            }
        });

    }

    public void savePost() {

        String title = editTitle.getText().toString();
        String details = editDetails.getText().toString();
        String duration = editDuration.getText().toString();
        String age = editAge.getText().toString();
        String disease = spDiseases.getSelectedItem().toString();
        String gender = spGender.getSelectedItem().toString();

        if (title.isEmpty() || details.isEmpty() || duration.isEmpty() || age.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Posts").child(userId).child(userKey).child("uid").setValue(userKey);
        mDatabase.child("Posts").child(userId).child(userKey).child("patientUid").setValue(userId);
        mDatabase.child("Posts").child(userId).child(userKey).child("title").setValue(title);
        mDatabase.child("Posts").child(userId).child(userKey).child("details").setValue(details);
        mDatabase.child("Posts").child(userId).child(userKey).child("duration").setValue(duration);
        mDatabase.child("Posts").child(userId).child(userKey).child("age").setValue(age);
        mDatabase.child("Posts").child(userId).child(userKey).child("doctor").setValue(doctor);
        mDatabase.child("Posts").child(userId).child(userKey).child("doctorUid").setValue(doctorUid);
        mDatabase.child("Posts").child(userId).child(userKey).child("disease").setValue(disease);
        mDatabase.child("Posts").child(userId).child(userKey).child("gender").setValue(gender);
        mDatabase.child("Posts").child(userId).child(userKey).child("isReport").setValue(isReport);

        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String item = adapterView.getItemAtPosition(0).toString();
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}