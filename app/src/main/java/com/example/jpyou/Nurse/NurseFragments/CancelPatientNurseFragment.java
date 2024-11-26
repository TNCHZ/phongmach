package com.example.jpyou.Nurse.NurseFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jpyou.Nurse.SchedulePatient;
import com.example.jpyou.Nurse.SchedulePatientAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelPatientNurseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelPatientNurseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CancelPatientNurseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelSchedulePatientNurseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelPatientNurseFragment newInstance(String param1, String param2) {
        CancelPatientNurseFragment fragment = new CancelPatientNurseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //====================================================================
    private ListView listView;
    private ArrayList<SchedulePatient> arrayList;
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
            SchedulePatient p1;
            p1 = new SchedulePatient("nguyen Van A", "aaa", R.drawable.ic_person);
            arrayList.add(p1);
            context = viewCancelPatient.getContext();
            adapter = new SchedulePatientAdapter(context, R.layout.row_list_patient, arrayList);
            listView.setAdapter(adapter);
        }
        return viewCancelPatient;
    }
}