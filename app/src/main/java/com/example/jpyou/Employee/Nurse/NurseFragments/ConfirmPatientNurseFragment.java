package com.example.jpyou.Employee.Nurse.NurseFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jpyou.Adapter.SchedulePatientAdapter;
import com.example.jpyou.MyDatabaseHelper;
import com.example.jpyou.User.UserInformation;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmPatientNurseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmPatientNurseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfirmPatientNurseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleNurseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmPatientNurseFragment newInstance(String param1, String param2) {
        ConfirmPatientNurseFragment fragment = new ConfirmPatientNurseFragment();
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

            adapter = new SchedulePatientAdapter(context, R.layout.row_list_patient, arrayList);
            listView.setAdapter(adapter);
        }
        return view;
    }
}