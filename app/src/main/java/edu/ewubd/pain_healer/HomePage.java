package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//kawshik

public class HomePage extends AppCompatActivity {

    private TextView ivPic1, ivPic2, ivPic3;
    private Button btnLogout;
    private String role,userId;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ivPic1 = findViewById(R.id.post);
        ivPic2 = findViewById(R.id.profile);
        ivPic3 = findViewById(R.id.admin);
        btnLogout = findViewById(R.id.btnLogout);

        ivPic1.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, PostActivity.class);
            startActivity(i);
        });

        ivPic2.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, ProfileActivity.class);
            startActivity(i);
        });

        ivPic3.setOnClickListener(view -> {
            Intent i2 = new Intent(HomePage.this, PostActivity.class);
            startActivity(i2);
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = snapshot.child("role").getValue().toString();
                if(role.equals("admin")){
                    ivPic3.setVisibility(View.VISIBLE);
                    ivPic1.setVisibility(View.GONE);
                }
                else {
                    ivPic3.setVisibility(View.GONE);
                    ivPic1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
}