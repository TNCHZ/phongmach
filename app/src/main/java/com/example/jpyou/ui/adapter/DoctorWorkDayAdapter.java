package com.example.jpyou.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class DoctorWorkDayAdapter extends BaseAdapter {
    private Context context;
    private Integer layout;
    private List<String> days;
    private MyDatabaseHelper db;
    private List<Doctor> doctors;

    public DoctorWorkDayAdapter(Context context, Integer layout, List<String> days) {
        this.context = context;
        this.layout = layout;
        this.days = days;
        db = new MyDatabaseHelper(context);
    }


    private class ViewHolder {
        TextView txtName;
        Spinner spn;
    }

    @Override
    public int getCount() {
        return days.size();
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtName = view.findViewById(R.id.txtday_rowListDoctorWork);
            holder.spn = view.findViewById(R.id.spinnerDoctors_rowListDoctorWork);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        doctors = new ArrayList<>();
        doctors = db.getDoctors();
        String day = days.get(i);

        holder.txtName.setText(day);
        List<String> doctorNames = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorNames.add(doctor.getHoTen()); // Giả sử `getName()` là phương thức trả về tên bác sĩ
        }

        // Tạo ArrayAdapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spn.setAdapter(adapter);

        holder.spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Doctor selectedDoctor = doctors.get(position); // Bác sĩ tại vị trí được chọn
                setConfirmDay(day, selectedDoctor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    public void setConfirmDay(String day, Doctor selectedDoctor) {
        boolean isInserted = db.updateDoctor(selectedDoctor.getId(), day);
        if (isInserted) {
            Toast.makeText(context, "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Lỗi khi lưu thông tin!", Toast.LENGTH_SHORT).show();
        }
    }
}
