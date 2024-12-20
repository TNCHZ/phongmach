package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jpyou.data.model.Medicine;
import com.example.myapplication.R;

import java.util.List;

public class ShowMedicineForPatientAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<Medicine> medicines;

    public ShowMedicineForPatientAdapter(Context context, Integer layout, List<Medicine> medicines) {
        this.context = context;
        this.layout = layout;
        this.medicines = medicines;
    }

    private class ViewHolder{
        TextView txtName, txtUnit, txtQuantity, txtUsage;
    }



    @Override
    public int getCount() {
        return medicines.size();
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
            holder.txtName = (TextView) view.findViewById(R.id.txtName_RowMedicineUser);
            holder.txtUnit = (TextView) view.findViewById(R.id.txtDV_RowMedicineUser);
            holder.txtQuantity = (TextView) view.findViewById(R.id.txtSL_RowMedicineUser);
            holder.txtUsage = (TextView) view.findViewById(R.id.txtHDSD_RowMedicineUser);
            view.setTag(holder);//Truyền trạng thái ánh xạ
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Medicine mc = medicines.get(i);
        holder.txtName.setText(mc.getName());
        holder.txtUnit.setText(mc.getUnit());
        holder.txtUsage.setText(mc.getUsage());
        holder.txtQuantity.setText(mc.getQuantity());
        return view;
    }
}
