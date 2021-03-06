package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ViewPost extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Post> posts;
    private CustomViewAdapter adapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        gridView = findViewById(R.id.gridview);
        mAuth = FirebaseAuth.getInstance();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post pst = posts.get(i);
                Intent intent = new Intent(ViewPost.this, ViewReport.class);
                intent.putExtra("postId", pst.getUid());
                startActivity(intent);
            }
        });


        loadData();
    }

    private void loadData() {
        posts = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Posts").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post p = snapshot.getValue(Post.class);
                    posts.add(p);
                }

                adapter = new CustomViewAdapter(ViewPost.this, posts);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewPost.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}