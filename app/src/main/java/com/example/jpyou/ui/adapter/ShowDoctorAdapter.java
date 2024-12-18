package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.ui.view.fragment.InformationDoctorUserFragment;
import com.example.jpyou.ui.view.fragment.MedicalRecordDoctorFragment;
import com.example.myapplication.R;

import java.util.List;

public class ShowDoctorAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<Doctor> doctors;
    private String userId;

    public ShowDoctorAdapter(Context context, Integer layout, List<Doctor> doctors, String userID) {
        this.context = context;
        this.layout = layout;
        this.doctors = doctors;
        this.userId = userID;
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtDescribe;
        Button btn;
        ImageView imgView;
    }


    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new ViewHolder();
            //Ánh xạ "id" từ view
            holder.txtName = (TextView) view.findViewById(R.id.textViewName_rowListDoctor);
            holder.txtDescribe = (TextView) view.findViewById(R.id.textViewDistriction_rowListDoctor);
            holder.imgView = (ImageView) view.findViewById(R.id.imageViewAvatar_rowListDoctor);
            holder.btn = (Button) view.findViewById(R.id.btnRegis_rowListDoctor);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Doctor ps = doctors.get(i);
        holder.txtName.setText(ps.getHoTen());
        holder.txtDescribe.setText("Kinh nghiệm: " + ps.getKinhNghiem());
        if (!userId.isEmpty()) {
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InformationDoctorUserFragment fragment = new InformationDoctorUserFragment(ps, userId);
                    ((FragmentActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_user_choose_doctor, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else
        {
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InformationDoctorUserFragment fragment = new InformationDoctorUserFragment(ps, userId);
                    ((FragmentActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_home, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
        holder.imgView.setImageResource(R.drawable.ic_person);
        return view;
    }
}