package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostDoctorActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Post> posts;
    private CustomViewAdapter adapter;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant);

        gridView = findViewById(R.id.gridview1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post pst = posts.get(i);
                Intent intent = new Intent(PostDoctorActivity.this, ReportActivity.class);
                intent.putExtra("postId", pst.getUid());
                startActivity(intent);
            }
        });

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadData();

    }

    private void loadData() {
        posts = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post p = snapshot.getValue(Post.class);

                   // if(userId.equals(p1.getDoctorUid())) {

                        System.out.println("toni title: " + p.getTitle());
                        System.out.println("toni details: " + p.getDoctorUid());

                    posts.add(p);
                    // }
                }

                adapter = new CustomViewAdapter(PostDoctorActivity.this, posts);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostDoctorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}