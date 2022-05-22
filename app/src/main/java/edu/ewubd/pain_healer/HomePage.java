package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class HomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    CardView adminPanel;
    String userId, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24);


        adminPanel = findViewById(R.id.admin_card);
        CardView doctorList = findViewById(R.id.report_card);
        CardView profile = findViewById(R.id.profile_card);
        CardView hospital = findViewById(R.id.hospital_card);
        CardView consult = findViewById(R.id.consult_card);
        CardView viewPost = findViewById(R.id.viewPost_card);
        Button btnLogout = findViewById(R.id.btnLogOut);

        viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ViewPost.class);
                startActivity(intent);
            }
        });

        hospital.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, HospitalListActivity.class);
            startActivity(i);
        });

        profile.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, ProfileActivity.class);
            startActivity(i);
        });

        doctorList.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, DoctorListActivity.class);
            startActivity(i);
        });

        consult.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, PostDoctorActivity.class);
            startActivity(i);
        });


        adminPanel.setOnClickListener(view -> {
            Intent i2 = new Intent(HomePage.this, AdminActivity.class);
            startActivity(i2);
        });


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = snapshot.child("role").getValue().toString();
                if (role.equalsIgnoreCase("admin")) {

                    adminPanel.setVisibility(View.VISIBLE);
                    viewPost.setVisibility(View.GONE);
                    consult.setVisibility(View.GONE);

                } else if(role.equalsIgnoreCase("Doctor")){
                    adminPanel.setVisibility(View.GONE);
                    consult.setVisibility(View.VISIBLE);
                    viewPost.setVisibility(View.GONE);

                }
                else if(role.equalsIgnoreCase("Patient")){
                    adminPanel.setVisibility(View.GONE);
                    consult.setVisibility(View.GONE);
                    viewPost.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomePage.this, SignInPage.class);
                startActivity(i);

            }
        });
    }


    // button to toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_home:
                Intent i1 = new Intent(HomePage.this, DoctorListActivity.class);
                startActivity(i1);
                Toast.makeText(this, "Doctor List", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_profile:
                Intent i2 = new Intent(HomePage.this, ProfileActivity.class);
                startActivity(i2);
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.action_report:
                Intent i4 = new Intent(HomePage.this, HospitalListActivity.class);
                startActivity(i4);
                Toast.makeText(this, "Hospital List", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_logout:
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent i5 = new Intent(HomePage.this, SignInPage.class);
                startActivity(i5);
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

