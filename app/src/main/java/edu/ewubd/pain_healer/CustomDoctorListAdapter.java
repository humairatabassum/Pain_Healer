package edu.ewubd.pain_healer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomDoctorListAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final ArrayList<User> values;


    public CustomDoctorListAdapter(@NonNull Context context, @NonNull ArrayList<User> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_doctor, parent, false);

        TextView doctorName = rowView.findViewById(R.id.tvDocName);
        TextView doctorTitle = rowView.findViewById(R.id.tvDocTitle);
        TextView doctorDepartment = rowView.findViewById(R.id.tvDocDepartment);

        doctorName.setText(values.get(position).getName());
        doctorTitle.setText (values.get(position).getTitle());
        doctorDepartment.setText(values.get(position).getDepartment());


        return rowView;
    }
}

