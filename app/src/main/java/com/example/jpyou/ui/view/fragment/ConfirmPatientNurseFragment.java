package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jpyou.ui.adapter.SchedulePatientAdapter;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Patient;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ConfirmPatientNurseFragment extends Fragment {
    private String userID;

    public ConfirmPatientNurseFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //====================================================================
    private ListView listView;
    private List<Patient> arrayList;
    private SchedulePatientAdapter adapter;
    private Context context;
    private MyDatabaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nurse_confirm_patient, container, false);
        {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("TaiKhoanID", null);

            db = new MyDatabaseHelper(getActivity());
            listView = (ListView) view.findViewById(R.id.list_ComfirmPatientNurseFragment);
            context = view.getContext();

            arrayList = new ArrayList<>();
            arrayList = db.showPatient();
            adapter = new SchedulePatientAdapter(context, R.layout.row_list_patient, arrayList, userID);
            listView.setAdapter(adapter);
            adapter.setOnConfirmClickListener(new SchedulePatientAdapter.OnConfirmClickListener() {
                @Override
                public void onConfirmClick(Patient patient, View view) {
                    if (db.confirmExam(patient.getId(), patient.getAppointDay())) {
                        Toast.makeText(context, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Đặt lịch thất bại", Toast.LENGTH_SHORT).show();
                    }
                    refreshList();
                }

            });
        }
        return view;
    }

    private void refreshList() {
        arrayList.clear();
        arrayList.addAll(db.showPatient());
        adapter.notifyDataSetChanged();
    }

}