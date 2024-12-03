package com.example.jpyou.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.TextView;


import com.example.jpyou.User.UserInformation;

import com.example.jpyou.init;
import com.example.myapplication.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowSchduleAndCancel extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<UserInformation> userInformation;

    public ShowSchduleAndCancel(Context context, Integer layout, List<UserInformation> userInformation) {
        this.context = context;
        this.layout = layout;
        this.userInformation = userInformation;
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
        return userInformation.size();
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

            //Ánh xạ "id" từ view
            holder.txtNameAppoint = (TextView) view.findViewById(R.id.textViewName_UserRowSchedule);
            holder.txtDay = (TextView) view.findViewById(R.id.textViewDay_UserRowSchedule);
            holder.btnCancel = (Button) view.findViewById(R.id.btnCancel_UserRowSchedule);
            holder.btnShow = (Button) view.findViewById(R.id.btnShow_UserRowSchedule);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Gán giá trị
        UserInformation ps = userInformation.get(i);
        holder.txtNameAppoint.setText(ps.getNameAppoint());
        holder.txtDay.setText(ps.getAppointDay());
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        String today = init.getCurrentDate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate1 = LocalDate.parse(ps.getAppointDay(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate localDate2 = LocalDate.parse(today, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (localDate2.isAfter(localDate1) || localDate2.isEqual(localDate1)) {
                holder.btnCancel.setVisibility(View.GONE);
            }
            else{
                holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }


        return view;
    }
}
