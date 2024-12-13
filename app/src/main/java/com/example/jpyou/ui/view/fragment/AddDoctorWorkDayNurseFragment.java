package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.ui.view.activity.CalendarEdit;
import com.example.jpyou.utils;
import com.example.myapplication.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddDoctorWorkDayNurseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MyDatabaseHelper(getActivity());
    }

    private MyDatabaseHelper db;
    private CalendarView clv;
    private Spinner sp;
    private Button btnConfirm;
    private List<Doctor> doctors;
    private String selectedDate, selectedDoctorName;
    private Doctor selectedDoctor;
    private List<CalendarEdit> calendarEditList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nurse_add_doctor_work_day, container, false);
        clv = view.findViewById(R.id.calendarView2_AddDoctorWorkDayNurseFragment);
        sp = view.findViewById(R.id.spinnerDoctors_AddDoctorWorkDayNurseFragment);
        btnConfirm = view.findViewById(R.id.btnConfirm_AddDoctorWorkDayNurseFragment);
        calendarEditList = new ArrayList<>();
        doctors = new ArrayList<>();
        doctors = db.getDoctors();
        List<String> doctorNames = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorNames.add(doctor.getHoTen());
        }

        selectedDate = utils.getCurrentDate();

        clv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                selectedDate = dayOfMonth + "/" + month + "/" + year;
            }
        });



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.d("1", selectedDate);
            LocalDate localDate1 = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate localDate2 = LocalDate.parse(utils.getCurrentDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (localDate2.isAfter(localDate1)) {
                btnConfirm.setVisibility(View.GONE);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctorName = doctorNames.get(position);
                selectedDoctor = doctors.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarEditList.add(new CalendarEdit(selectedDate, selectedDoctor));
                if (db.updateDoctor(selectedDoctor.getId(), selectedDate))
                    Toast.makeText(getActivity(), "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


}