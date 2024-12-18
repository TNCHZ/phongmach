package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.data.model.PersonInformation;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class InformationDoctorUserFragment extends Fragment {
    private Doctor doctor;
    private String userID;
    private Button btnBack, btnRegis;
    private TextView tvName, tvExp;
    private ListView lv;
    private MyDatabaseHelper db;
    private Spinner sp;
    private List<String> day;
    private PersonInformation ps;
    private String selectedDay;
    public InformationDoctorUserFragment() {
    }

    public InformationDoctorUserFragment(Doctor dt, String id) {
        doctor = dt;
        userID = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_information_doctor, container, false);
        btnRegis = view.findViewById(R.id.buttonRegis_InformationDoctorUserFragment);
        if(userID.isEmpty())
            btnRegis.setVisibility(View.GONE);
        tvName = view.findViewById(R.id.txtName_InformationDoctorUserFragment);
        tvExp = view.findViewById(R.id.txtExp_InformationDoctorUserFragment);
        tvName.setText(doctor.getHoTen());
        tvExp.setText(doctor.getKinhNghiem());
        db = new MyDatabaseHelper(getActivity());
        sp = view.findViewById(R.id.spinnerDay_InformationDoctorUserFragment);
        ps = db.getInformation(userID);


        btnBack = view.findViewById(R.id.buttonBack_InformationDoctorUserFragment);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        day = new ArrayList<>();
        day = db.getAllDayDoctorWork(doctor.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, day);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        btnRegis = view.findViewById(R.id.buttonRegis_InformationDoctorUserFragment);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.registerExamination(userID, selectedDay, "");
            }
        });






        return view;
    }
}