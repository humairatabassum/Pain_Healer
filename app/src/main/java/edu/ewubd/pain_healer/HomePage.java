package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    CardView adminPanel, Report;
    String userId,role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        //toolbar
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24);



        adminPanel = findViewById(R.id.admin_card);
        CardView report = findViewById(R.id.report_card);
        CardView profile = findViewById(R.id.profile_card);
        CardView post = findViewById(R.id.post_card);
        Button btnLogout = findViewById(R.id.btnLogOut);

        post.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, PostActivity.class);
            startActivity(i);
        });

        profile.setOnClickListener(view -> {
            Intent i = new Intent(HomePage.this, ProfileActivity.class);
            startActivity(i);
        });

        adminPanel.setOnClickListener(view -> {
            Intent i2 = new Intent(HomePage.this, AdminAcitivity.class);
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
                    adminPanel.setVisibility(View.VISIBLE);
                    report.setVisibility(View.GONE);

                }
                else {
                    adminPanel.setVisibility(View.GONE);
                    report.setVisibility(View.VISIBLE);

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





    // button to toolbar


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();


        if(id==R.id.action_notification){
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