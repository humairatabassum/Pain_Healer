package edu.ewubd.pain_healer;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomViewAdapter extends ArrayAdapter<Post> {

    private final Context context;
    private final ArrayList<Post> values;

    public CustomViewAdapter(@NonNull Context context, @NonNull ArrayList<Post> objects) {

        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_viewpost, parent, false);

        TextView title = rowView.findViewById(R.id.tvtitle);
        TextView details = rowView.findViewById(R.id.tvDetail);
        TextView age = rowView.findViewById(R.id.tvAge);
        TextView duration = rowView.findViewById(R.id.tvDuration);
        TextView disease = rowView.findViewById(R.id.tvDisease);
        TextView doctor = rowView.findViewById(R.id.tvDoctor);
        TextView department = rowView.findViewById(R.id.tvDepartment);


        title.setText("Title: " + values.get(position).getTitle());
        details.setText("Details: " + values.get(position).getDetails());
        age.setText("Patient's Age : " + values.get(position).getAge());
        duration.setText("Duration: " + values.get(position).getDuration());
        disease.setText("Existing Diseases(if any): " + values.get(position).getDisease());
        doctor.setText("Doctor: " + values.get(position).getDoctor());
        department.setText("Doctor's department : " + values.get(position).getDepartment());


        return rowView;
    }
}

