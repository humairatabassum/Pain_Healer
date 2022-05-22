package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String medTime1 ="", medTime2="", postUid, patientUid;
    private Button btnCancel, btnSubmit;
    private String isReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.PostBtn);

        patientUid = getIntent().getStringExtra("patientUid");
        postUid = getIntent().getStringExtra("postId");

        isReport = getIntent().getStringExtra("isReport");




        btnCancel.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase.getInstance().getReference("Posts").child(patientUid).child(postUid).child("isReport").setValue("true");

                saveReport();
                Intent intent = new Intent(ReportActivity.this, HomePage.class);
                startActivity(intent);
                finish();

                sendMail();

            }
        });

    }

    private void saveReport() {

        EditText med1Name = findViewById(R.id.etMedi1);
        EditText med2Name = findViewById(R.id.etMedi2);
        EditText suggestion = findViewById(R.id.etSuggestion);
        EditText testName = findViewById(R.id.etTest);
        EditText quantity1 = findViewById(R.id.etQuantity1);
        EditText quantity2 = findViewById(R.id.etQuantity2);
        EditText timeperiod1 = findViewById(R.id.timePeriod1);
        EditText timeperiod2 = findViewById(R.id.timePeriod2);
        EditText dateTime = findViewById(R.id.DateTime);

        CheckBox morning1 = findViewById(R.id.rdMorning1);
        CheckBox noon1 = findViewById(R.id.rdNoon1);
        CheckBox night1 = findViewById(R.id.rdNight1);
        CheckBox morning2 = findViewById(R.id.rdMorning2);
        CheckBox noon2 = findViewById(R.id.rdNoon2);
        CheckBox night2 = findViewById(R.id.rdNight2);

        mAuth = FirebaseAuth.getInstance();

        if (morning1.isChecked()) {
            medTime1 += " "+morning1.getText().toString();
        }else{
            medTime1 += " ";
        }
        if (noon1.isChecked()) {
            medTime1 += " "+ noon1.getText().toString();
        }else{
            medTime1 += " ";
        }
        if (night1.isChecked()) {
            medTime1 += " "+night1.getText().toString();
        }else{
            medTime1 += " ";
        }
        if (morning2.isChecked()) {
            medTime2 += " "+ morning2.getText().toString();
        }else{
            medTime1 += " ";
        }
        if (noon2.isChecked()) {
            medTime2 += " "+noon2.getText().toString();
        }else{
            medTime1 += " ";
        }
        if (night2.isChecked()) {
            medTime2 +=" "+ night2.getText().toString();
        }else{
            medTime1 += " ";
        }

        String med1 = med1Name.getText().toString();
        String med2 = med2Name.getText().toString();
        String suggestions = suggestion.getText().toString();
        String test = testName.getText().toString();
        String quan1 = quantity1.getText().toString();
        String quan2 = quantity2.getText().toString();
        String timePeriod1 = timeperiod1.getText().toString();
        String timePeriod2 = timeperiod2.getText().toString();
        String date = dateTime.getText().toString();

        String userId = mAuth.getCurrentUser().getUid();

        Report report = new Report(postUid, patientUid, med1, med2, quan1, quan2, suggestions, test, medTime1, medTime2 , timePeriod1, timePeriod2, date);
        FirebaseDatabase.getInstance().getReference("Reports")
                .child(patientUid).child(postUid)
                .setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ReportActivity.this, "Report Send successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendMail(){

                FirebaseDatabase.getInstance().getReference().child("Users").child(patientUid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                            String email = user.getEmail();

                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pain Healer : prescription posted for your problem");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + user.getName()+ "\n Get full prescription in your app");
                            startActivity(Intent.createChooser(emailIntent, ""));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                      Toast.makeText(ReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
               });


    }

}

