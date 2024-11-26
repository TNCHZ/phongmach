package com.example.jpyou.Nurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    //Lớp ViewHolder giúp tránh ánh xạ lặp đi lặp lại khi lướt lên xuống
    private class ViewHolder{
        TextView txtName;
        TextView txtDescribe;
        ImageView imgAvatar;
        Button btnComfirm;
        Button btnCancel;
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
        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new ViewHolder();
            //Ánh xạ "id" từ view
            holder.txtName = (TextView) view.findViewById(R.id.textViewName_NurseRowSchedule);
            holder.txtDescribe = (TextView) view.findViewById(R.id.textViewDescribe_NurseRowSchedule);
            holder.imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar_NurseRowSchedule);
            holder.btnComfirm = (Button) view.findViewById(R.id.btnConfirm_NurseRowSchedule);
            //holder.btnCancel = (Button) view.findViewById(R.id.btnCancel_NurseRowSchedule);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        }else {
            holder = (ViewHolder) view.getTag();
        }

        //Gán giá trị
        SchedulePatient schedulePatient = schedulePatientList.get(i);

        holder.txtName.setText(schedulePatient.getTxtName());
        holder.txtDescribe.setText(schedulePatient.getTxtDescribe());
        holder.imgAvatar.setImageResource(schedulePatient.getImgAvatar());
        holder.btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!schedulePatient.getBlConfirmed()){
                    schedulePatient.setBlConfirmed(true);
                    Toast.makeText(context.getApplicationContext(), "True",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (schedulePatient.getBlConfirmed()){
//                    schedulePatient.setBlConfirmed(false);
//                    Toast.makeText(context.getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        return view;
    }
}
