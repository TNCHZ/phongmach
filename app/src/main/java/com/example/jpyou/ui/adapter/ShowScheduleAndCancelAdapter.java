package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.FragmentActivity;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Patient;

import com.example.jpyou.ui.view.fragment.MedicalRecordDoctorFragment;
import com.example.jpyou.ui.view.fragment.ShowResultUserFragment;
import com.example.jpyou.utils;
import com.example.myapplication.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowScheduleAndCancelAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<Patient> patient;
    private MyDatabaseHelper db;

    public ShowScheduleAndCancelAdapter(Context context, Integer layout, List<Patient> patient) {
        this.context = context;
        this.layout = layout;
        this.patient = patient;
        db = new MyDatabaseHelper(context);
    }

    //Lớp ViewHolder giúp tránh ánh xạ lặp đi lặp lại khi lướt lên xuống
//    private class ViewHolder {
//        TextView txtNameAppoint;
//        TextView txtDay;
//        Button btnCancel;
//        Button btnShow;
//    }

    @Override
    public int getCount() {
        return patient.size();
    } //Hiển thị mỗi phần từ trong List

    @Override
    public Object getItem(int i) {
        return null;
    } //Trả ra đối tượng SchedulePatient

    @Override
    public long getItemId(int i) {
        return 0;
    } //Trả ra id của mỗi dòng listview


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
        view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
        db = new MyDatabaseHelper(context);
        TextView txtNameAppoint = (TextView) view.findViewById(R.id.textViewName_UserRowSchedule);
        TextView txtDay = (TextView) view.findViewById(R.id.textViewDay_UserRowSchedule);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel_UserRowSchedule);
        Button btnShow = (Button) view.findViewById(R.id.btnShow_UserRowSchedule);


        //Gán giá trị
        Patient ps = patient.get(i);
        txtNameAppoint.setText(ps.getNameAppoint());
        txtDay.setText(ps.getAppointDay());
        String today = utils.getCurrentDate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate1 = LocalDate.parse(ps.getAppointDay(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate localDate2 = LocalDate.parse(today, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (localDate2.isAfter(localDate1) || localDate2.isEqual(localDate1)) {
                btnCancel.setVisibility(View.GONE);
            } else {
                btnCancel.setOnClickListener(v -> {
                    if (cancelListener != null) {
                        cancelListener.onCancelClick(ps, v);  // Gọi phương thức của listener
                    }
                });
            }
        }
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundlePatientID = new Bundle();
                bundlePatientID.putString("patient_id", ps.getId());
                bundlePatientID.putString("NgayKham", ps.getAppointDay());
                ShowResultUserFragment fragment = new ShowResultUserFragment();
                fragment.setArguments(bundlePatientID);

                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_user_schedule, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    public interface OnCancelClickListener {
        void onCancelClick(Patient patient, View view);
    }

    private OnCancelClickListener cancelListener;

    public void setOnCancelClickListener(OnCancelClickListener listener) {
        this.cancelListener = listener;
    }

}
