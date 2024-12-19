package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CommentDoctorFragment extends Fragment {
    private String userID;
    private Spinner sp;
    private Button btnBack, btnConfirm;
    private EditText txtCMT;
    private MyDatabaseHelper db;
    private List<Doctor> doctors;
    private List<String> doctorNames;
    private Doctor selectedDoctor;
    private TextView tv;

    public CommentDoctorFragment(String userID) {
        this.userID = userID;
    }

    public CommentDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_doctor, container, false);
        sp = view.findViewById(R.id.spinnerDoctor_CommentDoctorFragment);
        btnBack = view.findViewById(R.id.buttonBack_CommentDoctorFragment);
        btnConfirm = view.findViewById(R.id.buttonConfirm_CommentDoctorFragment);
        txtCMT = view.findViewById(R.id.editTextText_CommentDoctorFragment);
        db = new MyDatabaseHelper(getActivity());
        tv = view.findViewById(R.id.textViewNameDoctor_CommentDoctorFragment);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        doctorNames = new ArrayList<>();
        doctors = new ArrayList<>();
        doctors = db.getDoctorsByPatientID(db.getPatientID(userID));

        if (!doctors.isEmpty()) {
            for (Doctor dc : doctors)
                doctorNames.add(dc.getHoTen());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctorNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tv.setText(doctorNames.get(position));
                    selectedDoctor = doctors.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    tv.setText(doctorNames.get(0));
                    selectedDoctor = doctors.get(0);
                }
            });
        } else {
            sp.setVisibility(View.GONE);

        }


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtCMT.getText().toString().isEmpty()) {
                    if (db.commentDoctor(db.getPatientID(userID), selectedDoctor, txtCMT.getText().toString())) {
                        Toast.makeText(getActivity(), "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "Chưa nhập đánh giá", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
}