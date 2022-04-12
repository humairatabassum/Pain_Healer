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



//    private TextView nameTextView, emailTextView, phoneTextVie;
//    private FirebaseDatabase database;
//    private DatabaseReference mDatabase;
//    private String email;
//    private String userid;
//    private Map<String, String> userMap;
//    private static final String USERS = "User";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        Intent intent = getIntent();
//        email = intent.getStringExtra("email");
//
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference userRef = rootRef.child(USERS);
//        Log.v("", userRef.getKey());
//
//        nameTextView = findViewById(R.id.tv3);
//        emailTextView = findViewById(R.id.tv4);
//        phoneTextVie = findViewById(R.id.tv5);
//
//       // mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        userRef.addValueEventListener(new ValueEventListener() {
//            String name, email, phone;
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
//                    if (keyId.child("email").getValue().equals(email)) {
//                        name = keyId.child("username").getValue(String.class);
//                        email = keyId.child("email").getValue(String.class);
//                        phone = keyId.child("phoneNumber").getValue(String.class);
//                        break;
//                    }
//                }
//                nameTextView.setText(name);
//                emailTextView.setText(email);
//                phoneTextVie.setText(phone);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(this.getClass().getName().toUpperCase(), "Failed to read value.", error.toException());
//            }
//        });
//    }

//}