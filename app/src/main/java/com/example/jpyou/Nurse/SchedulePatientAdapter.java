package com.example.jpyou.Nurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class SchedulePatientAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<SchedulePatient> schedulePatientList;

    public SchedulePatientAdapter(Context context, Integer layout, List<SchedulePatient> schedulePatientList) {
        this.context = context;
        this.layout = layout;
        this.schedulePatientList = schedulePatientList;
    }

    @Override
    public int getCount() {
        return schedulePatientList.size();
    } //Hiển thị mỗi phần từ trong List

    @Override
    public Object getItem(int i) {return null; } //Trả ra đối tượng SchedulePatient

    @Override
    public long getItemId(int i) {return 0; } //Trả ra id của mỗi dòng listview

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { //Trả về mỗi dòng trên item của ListView
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
        view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule

        //Ánh xạ "id" từ view
        TextView txtName = (TextView) view.findViewById(R.id.textViewName_NurseRowSchedule);
        TextView txtDescribe = (TextView) view.findViewById(R.id.textViewDescribe_NurseRowSchedule);
        ImageView imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar_NurseRowSchedule);
        Button btnComfirm = (Button) view.findViewById(R.id.btnConfirm_NurseRowSchedule);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel_NurseRowSchedule);

        //Gán giá trị
        SchedulePatient schedulePatient = schedulePatientList.get(i);

        txtName.setText(schedulePatient.getTxtName());
        txtDescribe.setText(schedulePatient.getTxtDescribe());
        imgAvatar.setImageResource(schedulePatient.getImgAvatar());
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!schedulePatient.getBlConfirmed()){
                    schedulePatient.setBlConfirmed(true);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (schedulePatient.getBlConfirmed()){
                    schedulePatient.setBlConfirmed(false);
                }
            }
        });


        return view;
    }
}
