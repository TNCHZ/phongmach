package com.example.jpyou.ListViewEdit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.jpyou.Employee.Doctor.DoctorFragments.MedicalRecordDoctorFragment;
import com.example.jpyou.Model.UserInformation;
import com.example.myapplication.R;

import java.util.List;

public class SchedulePatientAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<UserInformation> userInformation;

    public SchedulePatientAdapter(Context context, Integer layout, List<UserInformation> userInformation) {
        this.context = context;
        this.layout = layout;
        this.userInformation = userInformation;
    }

    //Lớp ViewHolder giúp tránh ánh xạ lặp đi lặp lại khi lướt lên xuống
    private class ViewHolder{
        TextView txtName;
        TextView txtDescribe;
        Button btnComfirm;
    }

    @Override
    public int getCount() {
        return userInformation.size();
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
            holder.txtName = (TextView) view.findViewById(R.id.textViewName_RowSchedule);
            holder.txtDescribe = (TextView) view.findViewById(R.id.textViewDescribe_RowSchedule);
            holder.btnComfirm = (Button) view.findViewById(R.id.btnConfirm_RowSchedule);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        }else {
            holder = (ViewHolder) view.getTag();
        }

        //Gán giá trị
        UserInformation ps = userInformation.get(i);
        holder.txtName.setText(ps.getHoTen());
        holder.txtDescribe.setText(ps.getTxtDescribe());
        holder.btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("patient_id", ps.getId());

                MedicalRecordDoctorFragment fragment = MedicalRecordDoctorFragment.newInstance("", "");
                fragment.setArguments(bundle);

                // Thay đổi Fragment
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_home, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}
