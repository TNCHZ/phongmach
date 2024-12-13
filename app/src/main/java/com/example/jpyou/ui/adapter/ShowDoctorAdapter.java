package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jpyou.data.model.Doctor;
import com.example.myapplication.R;

import java.util.List;

public class ShowDoctorAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<Doctor> doctors;

    public ShowDoctorAdapter(Context context, Integer layout, List<Doctor> doctors) {
        this.context = context;
        this.layout = layout;
        this.doctors = doctors;
    }

    private class ViewHolder{
        TextView txtName;
        TextView txtDescribe;
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
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new ViewHolder();
            //Ánh xạ "id" từ view
            holder.txtName = (TextView) view.findViewById(R.id.textViewName_rowListDoctor);
            holder.txtDescribe = (TextView) view.findViewById(R.id.textViewDistriction_rowListDoctor);
            holder.imgView = (ImageView) view.findViewById(R.id.imageViewAvatar_rowListDoctor);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Doctor ps = doctors.get(i);
        holder.txtName.setText(ps.getHoTen());
        holder.txtDescribe.setText("Kinh nghiệm: " + ps.getKinhNghiem());
        holder.imgView.setImageResource(R.drawable.ic_person);
        return view;
    }
}