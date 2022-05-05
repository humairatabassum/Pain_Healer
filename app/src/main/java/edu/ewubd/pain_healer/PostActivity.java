package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private EditText editTitle, editDetails, editDuration, editAge, editDoctor;
    private Spinner spDiseases,spDepartment;
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
        userKey = mDatabase.push().getKey();
        //userKey = mDatabase.child("Users").child(userId).push().getKey();


        editTitle = findViewById(R.id.etTitle);
        editDetails = findViewById(R.id.etDetails);
        editDuration = findViewById(R.id.etDuration);
        editAge = findViewById(R.id.etAge);
        editDoctor = findViewById(R.id.etDoctorName);
        spDiseases = findViewById(R.id.spDisease);
        spDepartment = findViewById(R.id.spDepartment);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diseases, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDiseases.setAdapter(adapter);
        spDiseases.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.department, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapter1);
        spDepartment.setOnItemSelectedListener(this);


        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });

    }

    public void savePost() {

        String title = editTitle.getText().toString();
        String details = editDetails.getText().toString();
        String duration = editDuration.getText().toString();
        String age = editAge.getText().toString();
        String doctor = editDoctor.getText().toString();
        String disease = spDiseases.getSelectedItem().toString();
        String department = spDepartment.getSelectedItem().toString();

        if (title.isEmpty() || details.isEmpty() || duration.isEmpty() || age.isEmpty() || doctor.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Posts").child(userId).child(userKey).child("Title").setValue(title);
        mDatabase.child("Posts").child(userId).child(userKey).child("Details").setValue(details);
        mDatabase.child("Posts").child(userId).child(userKey).child("Duration").setValue(duration);
        mDatabase.child("Posts").child(userId).child(userKey).child("Age").setValue(age);
        mDatabase.child("Posts").child(userId).child(userKey).child("Doctor").setValue(doctor);
        mDatabase.child("Posts").child(userId).child(userKey).child("Disease").setValue(disease);
        mDatabase.child("Posts").child(userId).child(userKey).child("Department").setValue(department);
        finish();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();

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


