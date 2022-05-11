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
import com.google.firebase.database.FirebaseDatabase;

public class ReportActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String medTime1 , medTime2 , docUid, patientUid;
    private Button btnCancel, btnSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.PostBtn);

        btnCancel.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReport();
            }
        });

        patientUid = getIntent().getStringExtra("uid");
        docUid = getIntent().getStringExtra("docUid");

    }

    private void saveReport(){

            EditText med1Name = findViewById(R.id.etMedi1);
            EditText med2Name = findViewById(R.id.etMedi2);
            EditText suggestion = findViewById(R.id.etSuggestion);
            EditText testName = findViewById(R.id.etTest);
            EditText quantity1 = findViewById(R.id.etQuantity1);
            EditText quantity2 = findViewById(R.id.etQuantity2);

            CheckBox morning1 = findViewById(R.id.rdMorning1);
            CheckBox noon1 = findViewById(R.id.rdNoon1);
            CheckBox night1 = findViewById(R.id.rdNight1);
            CheckBox morning2 = findViewById(R.id.rdMorning2);
            CheckBox noon2 = findViewById(R.id.rdNoon2);
            CheckBox night2 = findViewById(R.id.rdNight2);

            mAuth = FirebaseAuth.getInstance();

            if(morning1.isChecked()){
                medTime1 += morning1.getText().toString();
            }
            if(noon1.isChecked()){
                medTime1 += noon1.getText().toString();
            }
            if(night1.isChecked()){
                medTime1 += night1.getText().toString();
            }
            if(morning2.isChecked()){
                medTime2 += morning2.getText().toString();
            }
            if(noon2.isChecked()){
                medTime2 += noon2.getText().toString();
            }
            if(night2.isChecked()){
                medTime2 += night2.getText().toString();
            }

            String med1 = med1Name.getText().toString();
            String med2 = med2Name.getText().toString();
            String suggestions = suggestion.getText().toString();
            String test = testName.getText().toString();
            String quan1 = quantity1.getText().toString();
            String quan2 = quantity2.getText().toString();

            String userId = mAuth.getCurrentUser().getUid();

            Report report = new Report(docUid,patientUid, med1, med2, quan1, quan2, suggestions, test, medTime1, medTime2);
        FirebaseDatabase.getInstance().getReference("Reports")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ReportActivity.this, "Report Send successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ReportActivity.this, HomePage.class);
                        startActivity(intent);
                    }
                });






    }

}