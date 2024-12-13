package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.data.model.CalendarEdit;
import com.example.jpyou.utils;
import com.example.myapplication.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nurse_add_doctor_work_day, container, false);
        clv = view.findViewById(R.id.calendarView2_AddDoctorWorkDayNurseFragment);
        sp = view.findViewById(R.id.spinnerDoctors_AddDoctorWorkDayNurseFragment);
        btnConfirm = view.findViewById(R.id.btnConfirm_AddDoctorWorkDayNurseFragment);
        tv = view.findViewById(R.id.textviewConfirm_AddDoctorWorkDayNurseFragment);

        calendarEditList = new ArrayList<>();
        doctors = new ArrayList<>();
        doctors = db.getDoctors();

        calendarEditList = db.getDayDoctorWork();

        List<String> doctorNames = new ArrayList<>();
        doctorNames.add("");
        for (Doctor doctor : doctors) {
            doctorNames.add(doctor.getHoTen());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);


//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedDoctorName = doctorNames.get(position);
//                selectedDoctor = doctors.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Bỏ qua mục đầu tiên (mục trống)
                    selectedDoctorName = doctorNames.get(position);
                    selectedDoctor = doctors.get(position - 1); // Điều chỉnh chỉ số để khớp với danh sách `doctors`
                } else {
                    selectedDoctor = null; // Không có bác sĩ nào được chọn
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDoctor = null; // Không có lựa chọn
            }
        });
        selectedDate = utils.getCurrentDate();


        clv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                selectedDate = dayOfMonth < 10 ? "0" + dayOfMonth + "/" + month + "/" + year : dayOfMonth + "/" + month + "/" + year;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate localDate1 = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate localDate2 = LocalDate.parse(utils.getCurrentDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (localDate2.isAfter(localDate1)) {
                        btnConfirm.setVisibility(View.GONE);
                        sp.setEnabled(false);
                    } else {
                        btnConfirm.setVisibility(View.VISIBLE);
                        sp.setEnabled(true);
                    }

                    for (CalendarEdit calendarEdit : calendarEditList) {
                        LocalDate calendarDate = LocalDate.parse(calendarEdit.getDay(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        if (calendarDate.equals(localDate1)) {
                            int position = doctorNames.indexOf(calendarEdit.getDoctor().getHoTen());
                            sp.setSelection(position);
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("Bác sĩ đã được đặt lịch");
                            break;
                        }
                    }
                }
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("1", selectedDate);
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