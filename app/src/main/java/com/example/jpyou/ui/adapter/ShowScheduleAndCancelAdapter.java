package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Patient;

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
    private class ViewHolder {
        TextView txtNameAppoint;
        TextView txtDay;
        Button btnCancel;
        Button btnShow;
    }

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
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new ViewHolder();
            db = new MyDatabaseHelper(context);


            holder.txtNameAppoint = (TextView) view.findViewById(R.id.textViewName_UserRowSchedule);
            holder.txtDay = (TextView) view.findViewById(R.id.textViewDay_UserRowSchedule);
            holder.btnCancel = (Button) view.findViewById(R.id.btnCancel_UserRowSchedule);
            holder.btnShow = (Button) view.findViewById(R.id.btnShow_UserRowSchedule);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        } else {
            holder = (ViewHolder) view.getTag();
        }



        //Gán giá trị
        Patient ps = patient.get(i);
        holder.txtNameAppoint.setText(ps.getNameAppoint());
        holder.txtDay.setText(ps.getAppointDay());
        String today = utils.getCurrentDate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate1 = LocalDate.parse(ps.getAppointDay(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate localDate2 = LocalDate.parse(today, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (localDate2.isAfter(localDate1) || localDate2.isEqual(localDate1)) {
                holder.btnCancel.setVisibility(View.GONE);
            } else {
                holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(db.cancelDay(ps.getId()))
                            Toast.makeText(context, "Hủy lịch thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Hủy lịch thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }
}
