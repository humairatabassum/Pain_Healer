package edu.ewubd.pain_healer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    //retrive data from realtime database
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId;
    private TextView name, email, phone  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tvEmail);
        phone = findViewById(R.id.tvPhone);

        System.out.println("userId: " + userId);


        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                name.setText(user.getUsername());
                email.setText(user.getEmail());
                phone.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ProfileActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

//    public void onBackPressed() {
//        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
}