package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class ReportActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        EditText med1 = findViewById(R.id.etMedi1);
        EditText med2 = findViewById(R.id.etMedi2);
        EditText suggestion = findViewById(R.id.etSuggestion);
        EditText test = findViewById(R.id.etTest);

        CheckBox morning1 = findViewById(R.id.rdMorning1);
        CheckBox noon1 = findViewById(R.id.rdNoon1);
        CheckBox night1 = findViewById(R.id.rdNight1);
        CheckBox morning2 = findViewById(R.id.rdMorning2);
        CheckBox noon2 = findViewById(R.id.rdNoon2);
        CheckBox night2 = findViewById(R.id.rdNight2);

        mAuth = FirebaseAuth.getInstance();




    }
}