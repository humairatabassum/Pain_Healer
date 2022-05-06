package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button btnSignupPage,  btnLoginPage;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String userId, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId= mAuth.getCurrentUser().getUid();

            mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    role = snapshot.child("role").getValue().toString();

                    if(role.equals("patient")||role.equals("doctor")||role.equals("admin")){
                        System.out.println("User Role: "+ role);
                        Intent i = new Intent(MainActivity.this, HomePage.class);
                        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                    }else if(role.equals("pending")){
                        Intent i = new Intent(MainActivity.this, SignInPage.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        btnSignupPage = findViewById(R.id.btnSignUp);
        btnLoginPage = findViewById(R.id.logIN);

        btnSignupPage.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PatientRegistration.class);
            startActivity(i);
            System.out.println("signup Button");

        });

        btnLoginPage.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, SignInPage.class);
            startActivity(i);

        });

        System.out.println("Tonima" + btnLoginPage);

    }
}
