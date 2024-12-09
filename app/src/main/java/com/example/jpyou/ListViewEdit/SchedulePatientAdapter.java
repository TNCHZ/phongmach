package com.example.jpyou.ListViewEdit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.jpyou.Employee.Doctor.DoctorFragments.MedicalRecordDoctorFragment;
import com.example.jpyou.Model.UserInformation;
import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SchedulePatientAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<UserInformation> userInformation;
    private String userID;
    private MyDatabaseHelper db;



    public SchedulePatientAdapter(Context context, Integer layout, List<UserInformation> userInformation, String userID) {
        this.context = context;
        this.layout = layout;
        this.userInformation = userInformation;
        this.userID = userID;
        this.db = new MyDatabaseHelper(context); // Khởi tạo một lần
    }

    //Lớp ViewHolder giúp tránh ánh xạ lặp đi lặp lại khi lướt lên xuống
    private class ViewHolder {
        TextView txtName;
        TextView txtDescribe;
        Button btnComfirm;
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
    public View getView(int i, View view, ViewGroup viewGroup) { //Trả về mỗi dòng trên item của ListView
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new ViewHolder();
            //Ánh xạ "id" từ view
            holder.txtName = (TextView) view.findViewById(R.id.textViewName_RowSchedule);
            holder.txtDescribe = (TextView) view.findViewById(R.id.textViewDescribe_RowSchedule);
            holder.btnComfirm = (Button) view.findViewById(R.id.btnConfirm_RowSchedule);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Gán giá trị
        UserInformation ps = userInformation.get(i);
        holder.txtName.setText(ps.getHoTen());
        holder.txtDescribe.setText(ps.getTxtDescribe());
        if (db.getRole(userID).equals("Bac si")) {
            holder.btnComfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundlePatientID = new Bundle();
                    bundlePatientID.putString("patient_id", ps.getId());;
                    MedicalRecordDoctorFragment fragment = new MedicalRecordDoctorFragment();
                    fragment.setArguments(bundlePatientID);


                    ((FragmentActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_home, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }else {
            holder.btnComfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return view;
    }
}