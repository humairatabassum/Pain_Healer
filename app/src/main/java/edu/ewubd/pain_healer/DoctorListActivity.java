package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {

    private ListView listDoctors;
    private ArrayList<User> docs;
    private CustomDoctorListAdapter adapter;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        listDoctors = findViewById(R.id.listDoctor);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadData();

        listDoctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Clicked on " + docs.get(i).getName());
                User doctor = docs.get(i);
                Intent intent = new Intent(DoctorListActivity.this, PostActivity2.class);
                intent.putExtra("uid", doctor.getUid());
                startActivity(intent);

            }
        });

    }

    private void loadData() {
        docs = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user.getRole().equalsIgnoreCase("Doctor")) {
                        docs.add(user);
                    }
                }
                adapter = new CustomDoctorListAdapter(DoctorListActivity.this, docs);
                listDoctors.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DoctorListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }




}