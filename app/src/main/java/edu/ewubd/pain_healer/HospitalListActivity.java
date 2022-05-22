package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalListActivity extends AppCompatActivity {

    private EditText tvName, tvAddress, tvPhone;
    private ListView listHospital;
    private ArrayList<hospital> hosp;
    private CustomHospitalAdapter adapter;
    private FirebaseAuth mAuth;
    private String userId;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        Button addHospital = findViewById(R.id.btnAdd);
        addHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHospitals();
            }
        });



        mAuth = FirebaseAuth.getInstance();
        listHospital = findViewById(R.id.listView1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String role = snapshot.child("role").getValue().toString();
                if (role.equalsIgnoreCase("admin")) {
                    findViewById(R.id.rl1).setVisibility(View.VISIBLE);
                    listHospital.setVisibility(View.VISIBLE);


                } else if (role.equalsIgnoreCase("Patient") || role.equalsIgnoreCase("doctor")) {
                    findViewById(R.id.rl1).setVisibility(View.GONE);
                    listHospital.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HospitalListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        loadData();



    }

    private void loadData() {
        hosp = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Hospitals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hosp.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hospital hos = snapshot.getValue(hospital.class);

                    System.out.println(hos.getName());
                    hosp.add(hos);

                }
                adapter = new CustomHospitalAdapter(HospitalListActivity.this, hosp);
                listHospital.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HospitalListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

        private void addHospitals() {

        String userKey = mDatabase.push().getKey();
        tvName = findViewById(R.id.HospName);
        tvAddress = findViewById(R.id.address);
        tvPhone = findViewById(R.id.etPhoneNum);

        String name = tvName.getText().toString();
        String address = tvAddress.getText().toString();
        String phone = tvPhone.getText().toString();

        hospital hospital  = new hospital(name, address, phone);
        FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(userKey)
                .setValue(hospital).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HospitalListActivity.this, "Add hospital successfully", Toast.LENGTH_SHORT).show();
//                       Intent intent = new Intent(HospitalListActivity.this, HomePage.class);
//                       startActivity(intent);
                        finish();
                    }
                });

    }

  }