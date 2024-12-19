package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
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
import com.example.jpyou.data.model.Patient;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.ui.view.activity.SignIn;
import com.example.jpyou.ui.viewmodel.SharedViewModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;
    private Button btnRegisTreatMent, btnChooseDoctor, btnRegisForDoctor;
    private TableLayout layout;
    private List<Doctor> doctors;
    private List<Patient> patients;
    private ListView lv;
    private HorizontalScrollView scrview;
    private TextView tv;
    private SearchView sv;
    private LinearLayout ln, lnScrV;
    private Boolean isClick;


    public HomeFragment(String id)
    {
        userID = id;
    }

    public HomeFragment(Boolean iC)
    {
        isClick = iC != null;
    }

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint({"WrongThread", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        {
            if (getArguments() != null) {
                userID = getArguments().getString("userID");
            }

            db = new MyDatabaseHelper(getActivity());
            Intent intent = new Intent(getActivity(), SignIn.class);
            scrview = view.findViewById(R.id.scrollview_HomeFragment);
            lv = view.findViewById(R.id.listDSBS_HomeFragment);
            ln = view.findViewById(R.id.linearLayoutTop_HomeFragment);
            lnScrV = view.findViewById(R.id.linearLayoutScrollView_HomeFragment);
            tv = view.findViewById(R.id.textViewChange_HomeFragment);
            btnRegisForDoctor = view.findViewById(R.id.btnAddWorkDayForDoctor_HomeFragment);
            btnRegisTreatMent = view.findViewById(R.id.btnTreatment_HomeFragment);
            btnChooseDoctor = view.findViewById(R.id.btnChooseDoctor_HomeFragment);
            layout = view.findViewById(R.id.table_HomeFragment);
            sv = view.findViewById(R.id.searchView_HomeFragment);
            doctors = db.getDoctors();
            isClick = false;

            final Handler handler = new Handler();
            final Runnable scrollRunnable = new Runnable() {
                @Override
                public void run() {
                    int currentScrollX = scrview.getScrollX();
                    int maxScrollX = lnScrV.getWidth() - scrview.getWidth();

                    if (currentScrollX >= maxScrollX) {
                        scrview.smoothScrollTo(0, 0);  // Quay lại đầu
                    } else {
                        int nextPosition = currentScrollX + 700;
                        scrview.smoothScrollTo(nextPosition, 0);
                    }
                    handler.postDelayed(this, 2000);
                }
            };
            handler.postDelayed(scrollRunnable, 2000);


            if (userID == null) {
                ShowDoctorAdapter adapter = new ShowDoctorAdapter(getActivity(), R.layout.row_list_doctor, doctors, "");
                lv.setAdapter(adapter);
                layout.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
                btnRegisForDoctor.setVisibility(View.GONE);
            } else {
                if (db.getRole(userID).equals("Benh nhan")) {
                    tv.setVisibility(View.GONE);
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

                                RegisterForExaminationUserFragment anotherFragment = new RegisterForExaminationUserFragment(userID);
                                transaction.replace(R.id.fragment_home, anotherFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    });
                    btnChooseDoctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (userID == null) {
                                startActivity(intent);
                            } else {
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();

                                ChooseDoctorUserFragment anotherFragment = new ChooseDoctorUserFragment(userID, doctors);
                                transaction.replace(R.id.fragment_home, anotherFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    });
                } else if (db.getRole(userID).equals("Bac si")) {
                    scrview.setVisibility(View.GONE);
                    ln.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    sv.setVisibility(View.VISIBLE);
                    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            // Filter results when the user submits the query (optional)
                            filterPatient(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            filterPatient(newText);
                            return false;
                        }
                    });
                    btnRegisForDoctor.setVisibility(View.GONE);
                    tv.setText("Danh sách bệnh nhân");
                    patients = new ArrayList<>();
                    patients = db.showPatientForDoctor(db.getDoctorID(userID));
                    SchedulePatientAdapter adapterPatient = new SchedulePatientAdapter(getActivity(), R.layout.row_list_patient, patients, userID);
                    lv.setAdapter(adapterPatient);
                } else if (db.getRole(userID).equals("Y ta")){
                    scrview.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    sv.setVisibility(View.GONE);
                    btnRegisForDoctor.setVisibility(View.VISIBLE);
                    btnRegisForDoctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();

                            AddDoctorWorkDayNurseFragment anotherFragment = new AddDoctorWorkDayNurseFragment();
                            transaction.replace(R.id.fragment_home, anotherFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                    tv.setVisibility(View.GONE);
                }
            }


        }
        return view;
    }
    private void filterPatient(String query) {
        List<Patient> filteredList = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getAppointDay().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(patient);
            }
        }

        SchedulePatientAdapter filteredAdapter = new SchedulePatientAdapter(getActivity(), R.layout.row_medicine, filteredList,userID);
        lv.setAdapter(filteredAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isClick) {
            updateDataInHomeFragment();
        }
    }

    private void updateDataInHomeFragment() {
        patients = new ArrayList<>();
        patients = db.showPatientForDoctor(db.getDoctorID(userID));
        SchedulePatientAdapter adapterPatient = new SchedulePatientAdapter(getActivity(), R.layout.row_list_patient, patients, userID);
        lv.setAdapter(adapterPatient);
    }


}