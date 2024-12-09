package com.example.jpyou.BottomNavigator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jpyou.ListViewEdit.SchedulePatientAdapter;
import com.example.jpyou.ListViewEdit.ShowDoctor;
import com.example.jpyou.Model.Doctor;
import com.example.jpyou.Model.UserInformation;
import com.example.jpyou.MyDatabaseHelper;
import com.example.jpyou.User.UserFragments.RegisterForExaminationUserFragment;
import com.example.jpyou.SignIn;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;
    private Button btnRegisTreatMent,  btnChooseDoctor, btnResults;
    private TableLayout layout;
    private List<Doctor> doctors;
    private List<UserInformation> patients;
    private ListView lv;
    private HorizontalScrollView scrview;
    private TextView tv;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        {


            db = new MyDatabaseHelper(getActivity());
            Intent intent = new Intent(getActivity(), SignIn.class);
            scrview = view.findViewById(R.id.scrollview_HomeFragment);
            lv = view.findViewById(R.id.listDSBS_HomeFragment);
            tv = view.findViewById(R.id.textViewChange_HomeFragment);
            btnRegisTreatMent = view.findViewById(R.id.btnTreatment_HomeFragment);
            btnChooseDoctor = view.findViewById(R.id.btnChooseDoctor_HomeFragment);
            btnResults = view.findViewById(R.id.btnResults_HomeFragment);
            layout = view.findViewById(R.id.table_HomeFragment);

            doctors = db.getDoctors();
            ShowDoctor adapter = new ShowDoctor(getActivity(), R.layout.row_list_doctor, doctors);
            lv.setAdapter(adapter);

            if (userID == null) {
                layout.setVisibility(View.GONE);
            } else {
                if (db.getRole(userID).equals("Benh nhan")) {
                    layout.setVisibility(View.VISIBLE);
                    btnRegisTreatMent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (userID == null) {
                                startActivity(intent);
                            } else {
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                RegisterForExaminationUserFragment anotherFragment = new RegisterForExaminationUserFragment();
                                transaction.replace(R.id.fragment_home, anotherFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    });
                } else if(db.getRole(userID).equals("Bac si")){
                    scrview.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    patients = new ArrayList<>();
                    patients = db.showPatient();
                    SchedulePatientAdapter adapterPatient = new SchedulePatientAdapter(getActivity(), R.layout.row_list_patient, patients, userID);
                    lv.setAdapter(adapterPatient);
                    tv.setText("Danh sách bệnh nhân");
                }else
                    layout.setVisibility(View.VISIBLE);
            }


        }
        return view;
    }
}