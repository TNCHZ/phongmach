package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jpyou.data.model.Medicine;
import com.example.myapplication.R;

import java.util.List;

public class MedicalRecordAdapter extends BaseAdapter {

    private Context context;
    private Integer layout;
    private List<Medicine> medicines;


    public MedicalRecordAdapter(Context context, Integer layout, List<Medicine> medicines) {
        this.context = context;
        this.layout = layout;
        this.medicines = medicines;
    }

    //Lớp ViewHolder giúp tránh ánh xạ lặp đi lặp lại khi lướt lên xuống
    private class ViewHolder{
        TextView txtName, txtQuantity, txtUnit, txtUsage;
        Button btnCancel;
    }

    @Override
    public int getCount() {return medicines.size();}
    @Override
    public Object getItem(int i) {return null;}
    @Override
    public long getItemId(int i) {return 0;}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new ViewHolder();
            //Ánh xạ "id" từ view
            holder.txtName = view.findViewById(R.id.textViewName_DoctorRowMedicineChosen);
            holder.txtQuantity = view.findViewById(R.id.textViewQuantity_DoctorRowMedicineChosen);
            holder.txtUnit = view.findViewById(R.id.textViewUnit_DoctorRowMedicineChosen);
            holder.txtUsage = view.findViewById(R.id.textViewUsageInstruction_DoctorRowMedicineChosen);
            holder.btnCancel = view.findViewById(R.id.btnCancel_DoctorRowMedicineChosen);
            view.setTag(holder); //Truyền trạng thái ánh xạ
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Gán giá trị
        Medicine mc = medicines.get(i);
        holder.txtName.setText(mc.getName());
        holder.txtQuantity.setText(mc.getQuantity());
        holder.txtUnit.setText(mc.getUnit());
        holder.txtUsage.setText(mc.getUsage());
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicines.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}

