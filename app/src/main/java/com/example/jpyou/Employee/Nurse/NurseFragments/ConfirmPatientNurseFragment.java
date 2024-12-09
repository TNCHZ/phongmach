package com.example.jpyou.Employee.Nurse.NurseFragments;

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

import com.example.jpyou.ListViewEdit.SchedulePatientAdapter;
import com.example.jpyou.MyDatabaseHelper;
import com.example.jpyou.Model.UserInformation;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ConfirmPatientNurseFragment extends Fragment {
    private String userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }



    //====================================================================
    private ListView listView;
    private List<UserInformation> arrayList;
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
            db = new MyDatabaseHelper(getActivity());
            listView = (ListView) view.findViewById(R.id.list_ComfirmPatientNurseFragment);
            //Add các phiếu khám
            arrayList = new ArrayList<>();
            arrayList = db.showPatient();

            context = view.getContext();

            adapter = new SchedulePatientAdapter(context, R.layout.row_list_patient, arrayList, userID);
            listView.setAdapter(adapter);
        }
        return view;
    }
}