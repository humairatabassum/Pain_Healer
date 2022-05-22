package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewReport extends AppCompatActivity {

    private TextView med1, med2 , dose1 , dose2, time1, time2 , suggestion , test , timeperiod1 , timeperiod2, date;
    private FirebaseAuth mAuth;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        med1 = findViewById(R.id.Medi1);
        med2 = findViewById(R.id.Medi2);
        dose1 = findViewById(R.id.dose1);
        dose2 = findViewById(R.id.dose2);
        time1 = findViewById(R.id.Schedule1);
        time2 = findViewById(R.id.Schedule2);
        suggestion = findViewById(R.id.tvSuggetion);
        test = findViewById(R.id.tvTest);
        timeperiod1 = findViewById(R.id.tvtimePeriod1);
        timeperiod2 = findViewById(R.id.tvtimePeriod2);
        date = findViewById(R.id.tvDateTime);

        mAuth = FirebaseAuth.getInstance();
        String postUId = getIntent().getStringExtra("postId");

        FirebaseDatabase.getInstance().getReference().child("Reports").child(mAuth.getCurrentUser().getUid()).child(postUId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Report report = dataSnapshot.getValue(Report.class);

                if(report!=null) {


                    med1.setText(report.getMed1());
                    med2.setText(report.getMed2());
                    dose1.setText(report.getQuan1());
                    dose2.setText(report.getQuan2());
                    time1.setText(report.getMedTime1());
                    time2.setText(report.getMedTime2());
                    suggestion.setText(report.getSuggestions());
                    test.setText(report.getTest());
                    timeperiod1.setText(report.getTimePeriod1());
                    timeperiod2.setText(report.getTimePeriod2());
                    date.setText(report.getDate());
                }else {
                    Intent intent = new Intent(ViewReport.this, HomePage.class);
                    startActivity(intent);
                    Toast.makeText(ViewReport.this, "Your post is pending\n No report to show", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ProfileActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}