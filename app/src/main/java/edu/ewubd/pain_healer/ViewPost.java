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
    private ArrayList<Post> post;
    private CustomViewAdapter adapter;
    private FirebaseAuth mAuth;
    String userId, postId;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        gridView = findViewById(R.id.gridview);
        mAuth = FirebaseAuth.getInstance();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post pst = post.get(i);
                Intent intent = new Intent(ViewPost.this, ProfileActivity.class);
                intent.putExtra("postId", pst.getUid());

            }
        });


        loadData();
    }

    private void loadData() {
        post = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Posts").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post p = snapshot.getValue(Post.class);
                    post.add(p);
                    System.out.println("toni title"+p.getTitle());
                    System.out.println("toni uid"+p.getUid());
                    System.out.println("toni age"+p.getAge());
                    System.out.println("toni details"+p.getDetails());
                }

                adapter = new CustomViewAdapter(ViewPost.this, post);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewPost.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}