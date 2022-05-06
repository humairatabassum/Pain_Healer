package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAcitivity extends AppCompatActivity {

    private ListView listDoctor;
    private ArrayList<doctor> doc = new ArrayList<>();
    private CustomAdminAdapter adapter;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String userId, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listDoctor = findViewById(R.id.listview);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


//        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //get all user from Users firebase
//
//
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    doctor doctor = dataSnapshot.getValue(Users.class);
//                    if (doctor.getRole().equals("pending")) {
//                        doc.add(doctor);
//                        System.out.println("csr role" + role);
//                        try {
//                            adapter = new CustomAdminAdapter(AdminAcitivity.this, doc);
//                            gridDoctor.setAdapter(adapter);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                        //doctor.add(doctor); //add all doctor to arraylist
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
                                                                                              }

       // userId = mAuth.getCurrentUser().getUid();





    }
