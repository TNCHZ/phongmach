package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
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
import com.example.jpyou.utils;
import com.example.myapplication.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AddDoctorWorkDayNurseFragment extends Fragment {

    public AddDoctorWorkDayNurseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MyDatabaseHelper(getActivity());
    }

    private MyDatabaseHelper db;
    private CalendarView clv;
    private Spinner sp;
    private Button btnConfirm, btnBack;
    private List<Doctor> doctors;
    private String selectedDate, selectedDoctorName;
    private Doctor selectedDoctor;
    private TextView tv1, tv2;
    private String tenBacSi;
    private Doctor lastDoctor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nurse_add_doctor_work_day, container, false);
        {
            clv = view.findViewById(R.id.calendarView2_AddDoctorWorkDayNurseFragment);
            sp = view.findViewById(R.id.spinnerDoctors_AddDoctorWorkDayNurseFragment);
            btnConfirm = view.findViewById(R.id.btnConfirm_AddDoctorWorkDayNurseFragment);
            tv1 = view.findViewById(R.id.textviewConfirm_AddDoctorWorkDayNurseFragment);
            tv2 = view.findViewById(R.id.textview_AddDoctorWorkDayNurseFragment);
            btnBack = view.findViewById(R.id.buttonBack_AddDoctorWorkDayNurseFragment);
            tenBacSi = null;
            doctors = new ArrayList<>();
            doctors = db.getDoctors();

            List<String> doctorNames = new ArrayList<>();
            for (Doctor doctor : doctors) {
                doctorNames.add(doctor.getHoTen());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctorNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);

            selectedDate = utils.getCurrentDate();

            if (db.getDoctorWorkAtDay(selectedDate) != null) {
                tenBacSi = db.getDoctorWorkAtDay(selectedDate).getHoTen();
                lastDoctor = db.getDoctorWorkAtDay(selectedDate);
            }

            if (tenBacSi != null) {
                tv1.setVisibility(View.VISIBLE);
                tv1.setText("Bác sĩ " + tenBacSi + " đã được đặt lịch làm");
            } else {
                tv1.setVisibility(View.GONE);
            }

            clv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    tv1.setVisibility(View.GONE);
                    tv2.setVisibility(View.VISIBLE);
                    month = month + 1;
                    selectedDate = dayOfMonth < 10 ? "0" + dayOfMonth + "/" + month + "/" + year : dayOfMonth + "/" + month + "/" + year;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        LocalDate localDate1 = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        LocalDate localDate2 = LocalDate.parse(utils.getCurrentDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        if (db.getDoctorWorkAtDay(selectedDate) != null) {
                            tenBacSi = db.getDoctorWorkAtDay(selectedDate).getHoTen();
                        }else
                            tenBacSi = null;

                        if (localDate2.isAfter(localDate1)) {
                            tv2.setVisibility(View.GONE);
                            btnConfirm.setVisibility(View.GONE);
                            sp.setVisibility(View.GONE);
                            sp.setEnabled(false);
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            sp.setVisibility(View.VISIBLE);
                            sp.setEnabled(true);
                        }
                        if (tenBacSi != null) {
                            tv1.setVisibility(View.VISIBLE);
                            tv1.setText("Bác sĩ " + tenBacSi + " đã được đặt lịch làm");
                        }
                    }
                }
            });

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedDoctorName = doctorNames.get(position);
                    selectedDoctor = doctors.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    if (!doctorNames.isEmpty() && !doctors.isEmpty()) {
                        selectedDoctorName = doctorNames.get(0);
                        selectedDoctor = doctors.get(0);
                    }
                }
            });


            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isSuccess = false;
                    if (tenBacSi == null) {
                        isSuccess = db.updateDoctor(selectedDoctor.getId(), selectedDate);
                        Toast.makeText(getActivity(), isSuccess ? "Đặt lịch thành công" : "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                    } else {
                        isSuccess = db.changeDoctorWork(selectedDoctor.getId(), selectedDate, lastDoctor.getId());
                        Toast.makeText(getActivity(), isSuccess ? "Thay đổi bác sĩ thành công" : "Thay đổi bác sĩ thất bại", Toast.LENGTH_SHORT).show();
                    }

                    // Nếu thành công, cập nhật lại giao diện
                    if (isSuccess) {
                        tenBacSi = db.getDoctorWorkAtDay(selectedDate).getHoTen(); // Lấy lại tên bác sĩ
                        if (tenBacSi != null) {
                            tv1.setVisibility(View.VISIBLE);
                            tv1.setText("Bác sĩ " + tenBacSi + " đã được đặt lịch làm");
                        } else {
                            tv1.setVisibility(View.GONE);
                        }
                    }
                }
            });

        }
        return view;
    }

}