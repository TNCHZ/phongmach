package com.example.jpyou.Employee.Nurse.NurseFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jpyou.ListViewEdit.SchedulePatientAdapter;
import com.example.jpyou.Model.UserInformation;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CancelPatientNurseFragment extends Fragment {
    private String userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }


    //====================================================================
    private ListView listView;
    private ArrayList<UserInformation> arrayList;
    private SchedulePatientAdapter adapter;
    private Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewCancelPatient = inflater.inflate(R.layout.fragment_nurse_cancel_patient, container, false);
        {
            listView = (ListView) viewCancelPatient.findViewById(R.id.list_CancelPatientNurseFragment);
            arrayList = new ArrayList<>();
//            UserInformation p1;
//            p1 = new UserInformation("nguyen Van A", "aaa", );
//            arrayList.add(p1);
            context = viewCancelPatient.getContext();
            adapter = new SchedulePatientAdapter(context, R.layout.row_list_patient, arrayList, userID);
            listView.setAdapter(adapter);
        }
        return viewCancelPatient;
    }
}