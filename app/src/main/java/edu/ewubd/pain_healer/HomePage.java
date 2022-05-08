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
        CardView report = findViewById(R.id.report_card);
        CardView profile = findViewById(R.id.profile_card);
        CardView post = findViewById(R.id.post_card);
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

        post.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, PostActivity.class);
            startActivity(i);
        });

        profile.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, ProfileActivity.class);
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
                    report.setVisibility(View.GONE);
                } else if(role.equalsIgnoreCase("Doctor")){
                    adminPanel.setVisibility(View.GONE);
                    report.setVisibility(View.VISIBLE);
                    consult.setVisibility(View.VISIBLE);
                    post.setVisibility(View.GONE);

                }
                else if(role.equalsIgnoreCase("Patient")){
                    adminPanel.setVisibility(View.GONE);
                    report.setVisibility(View.VISIBLE);
                    consult.setVisibility(View.GONE);
                    post.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(HomePage.this, HomePage.class);
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_profile:
                Intent i2 = new Intent(HomePage.this, ProfileActivity.class);
                startActivity(i2);
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.action_report:
                //Intent i4 = new Intent(HomePage.this, ReportActivity.class);
                //startActivity(i4);
                Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_logout:
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent i5 = new Intent(HomePage.this, SignInPage.class);
                startActivity(i5);
                Toast.makeText(this, "Loged Out", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


//  <--_________________________ Humur code start____________________________________________________________-->

//public class HomePage extends AppCompatActivity {
//
//    private TextView ivPic1, ivPic2, ivPic3;
//    private Button btnLogout;
//    private String role,userId;
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
//
//        ivPic1 = findViewById(R.id.post);
//        ivPic2 = findViewById(R.id.profile);
//        ivPic3 = findViewById(R.id.admin);
//        btnLogout = findViewById(R.id.btnLogout);
//
//        ivPic1.setOnClickListener(view -> {
//            Intent i = new Intent(HomePage.this, PostActivity.class);
//            startActivity(i);
//        });
//
//        ivPic2.setOnClickListener(view -> {
//            Intent i = new Intent(HomePage.this, ProfileActivity.class);
//            startActivity(i);
//        });
//
//        ivPic3.setOnClickListener(view -> {
//            Intent i2 = new Intent(HomePage.this, PostActivity.class);
//            startActivity(i2);
//        });
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        userId = mAuth.getCurrentUser().getUid();
//
//        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                role = snapshot.child("role").getValue().toString();
//                if(role.equals("admin")){
//                    ivPic3.setVisibility(View.VISIBLE);
//                    ivPic1.setVisibility(View.GONE);
//                }
//                else {
//                    ivPic3.setVisibility(View.GONE);
//                    ivPic1.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                FirebaseAuth.getInstance().signOut();
//                Intent i = new Intent(HomePage.this, SignInPage.class);
//                startActivity(i);
//
//            }
//        });
//    }
//}

//<!--_________________________ Humur code end____________________________________________________________-->