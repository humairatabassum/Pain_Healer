package edu.ewubd.pain_healer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomHospitalAdapter extends ArrayAdapter<hospital> {

    private final Context context;
    private final ArrayList<hospital> values;


    public CustomHospitalAdapter(@NonNull Context context, @NonNull ArrayList<hospital> objects) {
        super(context,-1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_hospital, parent, false);

        TextView name = rowView.findViewById(R.id.tvHosName);
        TextView address = rowView.findViewById(R.id.tvAddress);
        TextView phoneNum = rowView.findViewById(R.id.tvPhoneNum);

        name.setText(values.get(position).getName());
        address.setText (values.get(position).getAddress());
        phoneNum.setText(values.get(position).getPhone());

        phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNum.getText().toString()));
                context.startActivity(intent);
            }
        });


        return rowView;
    }
}


