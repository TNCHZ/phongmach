package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Role;
import com.example.jpyou.ui.view.fragment.ShowPersonInformationAdminFragment;
import com.example.myapplication.R;

import java.util.List;

public class ShowInformationUserAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<Role> ls;
    private MyDatabaseHelper db;

    public ShowInformationUserAdapter(Context context, Integer layout, List<Role> ls) {
        this.context = context;
        this.layout = layout;
        this.ls = ls;
        db = new MyDatabaseHelper(context);
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtDescribe;
        TextView txtWorking;
        Button btnShow;
    }

    @Override
    public int getCount() {
        return ls.size();
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
            holder.txtName = (TextView) view.findViewById(R.id.textViewName_RowInformation);
            holder.txtDescribe = (TextView) view.findViewById(R.id.textViewDescribe_RowInformation);
            holder.txtWorking = (TextView) view.findViewById(R.id.textViewWorking_RowInformation2);
            holder.btnShow = (Button) view.findViewById(R.id.btnShow_RowInformation);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Role rs = ls.get(i);
        holder.txtName.setText(rs.getPs().getHoTen());
        holder.txtDescribe.setText("Vai trò: " + rs.getRole());
        holder.txtWorking.setText(db.isAccountActive(rs.getPs().getId()) == true ? "Tài khoản còn hoạt động" : "Tài khoản bị khóa hoạt động");
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundlePatientID = new Bundle();

                bundlePatientID.putString("patient_id", rs.getPs().getId());
                ShowPersonInformationAdminFragment fragment = new ShowPersonInformationAdminFragment();
                fragment.setArguments(bundlePatientID);

                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmnent_admin_list_user, fragment)
                        .commit();
            }
        });
        return view;
    }
}
