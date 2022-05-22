package edu.ewubd.pain_healer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomAdminAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final ArrayList<User> values;


    public CustomAdminAdapter(@NonNull Context context, @NonNull ArrayList<User> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.admin_list, parent, false);

        TextView doctorName = rowView.findViewById(R.id.tvDocName);
        TextView doctorEmail = rowView.findViewById(R.id.tvDocEmail);
        TextView doctorNid = rowView.findViewById(R.id.tvDocNid);
        TextView doctorRegister = rowView.findViewById(R.id.tvDocRegister);

        Button accept = rowView.findViewById(R.id.btnAccept);
        Button reject = rowView.findViewById(R.id.btnReject);

        doctorName.setText("Name: " + values.get(position).getName());
        doctorEmail.setText("Email: " + values.get(position).getEmail());
        doctorNid.setText("Nid: " + values.get(position).getNid());
        doctorRegister.setText("BMDC: " + values.get(position).getRegister());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Users").child(values.get(position).getUid()).child("role").setValue("Doctor");

                FirebaseDatabase.getInstance().getReference().child("Users").child(values.get(position).getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        String email = user.getEmail();
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pain Healer : Registration Confirmation Email");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Name, " + user.getName()+ "\n your account id is accepted as a doctor.\nWelcome to Pain Healer");
                        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Users").child(values.get(position).getUid()).child("role").setValue("Patient");
            }
        });

        return rowView;
    }

}

