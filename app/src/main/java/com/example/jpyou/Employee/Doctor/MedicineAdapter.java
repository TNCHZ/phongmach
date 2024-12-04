package com.example.jpyou.Employee.Doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;

public class MedicineAdapter extends ArrayAdapter<Medicine> {

    public MedicineAdapter(@NonNull Context context, int resource, @NonNull List<Medicine> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medicine, parent, false);
        TextView textViewName = convertView.findViewById(R.id.textViewName_Row_Medicine);

        Medicine medicine = this.getItem(position);
        if (medicine != null){
            textViewName.setText(medicine.getName());
        }

        return convertView;
    }
}
