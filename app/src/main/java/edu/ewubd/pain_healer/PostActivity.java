package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private EditText editTitle, editDetails, editDuration, editAge, editDoctor;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userKey;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        System.out.println("userId: " + userId);

        userKey = mDatabase.child("Users").child(userId).push().getKey();

        System.out.println("userKey: " + userKey);


        editTitle = findViewById(R.id.etTitle);
        editDetails = findViewById(R.id.etDetails);
        editDuration = findViewById(R.id.etDuration);
        editAge = findViewById(R.id.etAge);
        editDoctor = findViewById(R.id.etDoctorName);
//        Spinner spDiseases = findViewById(R.id.spDisease);
//        Spinner spDepartment = findViewById(R.id.spDepartment);
        //dropdown access for disease and department





        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
            });

    }

    public void savePost(){

        String title = editTitle.getText().toString();
        String details = editDetails.getText().toString();
        String duration = editDuration.getText().toString();
        String age = editAge.getText().toString();
        String doctor = editDoctor.getText().toString();
//        String disease = spDiseases.getSelectedItem().toString();
//        String department = spDepartment.getSelectedItem().toString();

        if (title.isEmpty()||details.isEmpty()||duration.isEmpty()||age.isEmpty()||doctor.isEmpty()){
            Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Posts").child(userId).child(userKey).child("Title").setValue(title);
        mDatabase.child("Posts").child(userId).child(userKey).child("Details").setValue(details);
        mDatabase.child("Posts").child(userId).child(userKey).child("Duration").setValue(duration);
        mDatabase.child("Posts").child(userId).child(userKey).child("Age").setValue(age);
        mDatabase.child("Posts").child(userId).child(userKey).child("Doctor").setValue(doctor);
//                mDatabase.child("Posts").child(userId).child("Disease").setValue(disease);
//                mDatabase.child("Posts").child(userId).child("Department").setValue(department);
        finish();
    }
}


