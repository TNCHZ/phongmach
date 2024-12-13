package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.ui.adapter.DoctorWorkDayAdapter;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddDoctorWorkDayNurseFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MyDatabaseHelper(getActivity());
    }
    private Button btnConfirm;
    private ListView lv;
    private MyDatabaseHelper db;
    private Spinner sp;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nurse_add_doctor_work_day, container, false);
        sp = view.findViewById(R.id.spinnerMonth_AddDoctorWorkDayNurseFragment);
        String[] months = {
                "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),  // Context
                android.R.layout.simple_spinner_item, // Layout hiển thị
                months            // Dữ liệu
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedMonth = position;
                List<String> daysInMonth = getDaysInMonth(selectedMonth);
                DoctorWorkDayAdapter adapter1 = new DoctorWorkDayAdapter(getActivity(), R.layout.row_list_doctor_work, daysInMonth);
                lv.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConfirm = view.findViewById(R.id.btnConfirm_AddDoctorWorkDayNurseFragment);
        lv = view.findViewById(R.id.listView_AddDoctorWorkDayNurseFragment);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    private List<String> getDaysInMonth(int month) {
        List<String> days = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(year, month, day);
            String formattedDate = dateFormat.format(calendar.getTime());
            days.add(formattedDate);
        }

        return days;
    }


}