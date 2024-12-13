package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jpyou.ui.adapter.SchedulePatientAdapter;
import com.example.jpyou.ui.adapter.ShowDoctorAdapter;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.data.model.UserInformation;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.ui.view.activity.SignIn;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;
    private Button btnRegisTreatMent, btnChooseDoctor, btnResults, btnRegisForDoctor;
    private TableLayout layout;
    private List<Doctor> doctors;
    private List<UserInformation> patients;
    private ListView lv;
    private HorizontalScrollView scrview;
    private TextView tv;
    private SearchView sv;
    private LinearLayout ln;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        {
            db = new MyDatabaseHelper(getActivity());
            Intent intent = new Intent(getActivity(), SignIn.class);
            scrview = view.findViewById(R.id.scrollview_HomeFragment);
            lv = view.findViewById(R.id.listDSBS_HomeFragment);
            ln = view.findViewById(R.id.linearLayoutTop_HomeFragment);
            tv = view.findViewById(R.id.textViewChange_HomeFragment);
            btnRegisForDoctor = view.findViewById(R.id.btnAddWorkDayForDoctor_HomeFragment);
            btnRegisTreatMent = view.findViewById(R.id.btnTreatment_HomeFragment);
            btnChooseDoctor = view.findViewById(R.id.btnChooseDoctor_HomeFragment);
            btnResults = view.findViewById(R.id.btnResults_HomeFragment);
            layout = view.findViewById(R.id.table_HomeFragment);
            sv = view.findViewById(R.id.searchView_HomeFragment);


            if (userID == null) {
                doctors = db.getDoctors();
                ShowDoctorAdapter adapter = new ShowDoctorAdapter(getActivity(), R.layout.row_list_doctor, doctors);
                lv.setAdapter(adapter);
                layout.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
                btnRegisForDoctor.setVisibility(View.GONE);
            } else {
                if (db.getRole(userID).equals("Benh nhan")) {
                    layout.setVisibility(View.VISIBLE);
                    sv.setVisibility(View.GONE);
                    btnRegisForDoctor.setVisibility(View.GONE);
                    btnRegisTreatMent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (userID == null) {
                                startActivity(intent);
                            } else {
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();

                                RegisterForExaminationUserFragment anotherFragment = new RegisterForExaminationUserFragment();
                                transaction.replace(R.id.fragment_home, anotherFragment); // Thay thế Fragment hiện tại bằng Fragment mới
                                transaction.addToBackStack(null); // Thêm vào BackStack để hỗ trợ quay lại nếu cần
                                transaction.commit();

                            }
                        }
                    });
                } else if (db.getRole(userID).equals("Bac si")) {
                    scrview.setVisibility(View.GONE);
                    ln.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    sv.setVisibility(View.VISIBLE);
                    btnRegisForDoctor.setVisibility(View.GONE);
                    tv.setText("Danh sách bệnh nhân");
                    patients = new ArrayList<>();
                    patients = db.showPatientForDoctor(db.getDoctorID(userID));
                    SchedulePatientAdapter adapterPatient = new SchedulePatientAdapter(getActivity(), R.layout.row_list_patient, patients, userID);
                    lv.setAdapter(adapterPatient);
                } else {
                    sv.setVisibility(View.VISIBLE);
                    scrview.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    btnRegisForDoctor.setVisibility(View.VISIBLE);
                    btnRegisForDoctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            
                        }
                    });
                    tv.setVisibility(View.GONE);
                }
            }


        }
        return view;
    }
}