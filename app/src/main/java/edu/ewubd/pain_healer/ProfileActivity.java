package edu.ewubd.pain_healer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId, role;
    private TextView name, email, phone , gender , status , title , nid , bmdc , department;
    private ImageView profilePic;
    private Uri imgUrl;
    private Button uploadBtn;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();

        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tvEmail);
        phone = findViewById(R.id.tvPhone);
        gender = findViewById(R.id.tvGender);
        status = findViewById(R.id.tvRole);
        profilePic=findViewById(R.id.img_profile);
        uploadBtn= findViewById(R.id.btn_upload);
        title = findViewById(R.id.tvTitle);
        nid = findViewById(R.id.tvNid);
        bmdc = findViewById(R.id.tvBMDC);
        department = findViewById(R.id.tvDepartment);


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = snapshot.child("role").getValue().toString();
                if (role.equalsIgnoreCase("doctor")){
                    title.setVisibility(View.VISIBLE);
                    nid.setVisibility(View.VISIBLE);
                    bmdc.setVisibility(View.VISIBLE);
                    department.setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_s_nid).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_s_bmdc).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_s_department).setVisibility(View.VISIBLE);
                } else if (role.equalsIgnoreCase("Patient")||role.equalsIgnoreCase("admin")){
                    title.setVisibility(View.GONE);
                    nid.setVisibility(View.GONE);
                    bmdc.setVisibility(View.GONE);
                    department.setVisibility(View.GONE);
                    findViewById(R.id.tv_s_nid).setVisibility(View.GONE);
                    findViewById(R.id.tv_s_bmdc).setVisibility(View.GONE);
                    findViewById(R.id.tv_s_department).setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





                    mDatabase.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                name.setText(user.getName());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                gender.setText(user.getGender());
                status.setText(user.getRole());
                title.setText(user.getTitle());
                nid.setText(user.getNid());
                department.setText(user.getDepartment());
                bmdc.setText(user.getRegister());

                try {
                    Glide.with(ProfileActivity.this).load(user.getImageLink()).into(profilePic);
                } catch (Exception e) {
                    profilePic.setImageResource(R.drawable.image);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ProfileActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });
profilePic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
});

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();
            Glide.with(ProfileActivity.this).load(imgUrl).into(profilePic);
        } else {
            Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadFile() {
        if (imgUrl != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));

            fileReference.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                    //    progressBar.setProgress(0);
                    }, 5000);

                    Toast.makeText(ProfileActivity.this, "Upload successful.", Toast.LENGTH_LONG).show();
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri uri = urlTask.getResult();
                    String downloadImageUrl= uri.toString();
                    System.out.println("Download URL:  " + downloadImageUrl);

                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("imageLink", downloadImageUrl);
                    databaseRef.updateChildren(hashMap);
                    Toast.makeText(ProfileActivity.this, "Image uploaded.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProfileActivity.this, HomePage.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
//    public void onBackPressed() {
//        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
}